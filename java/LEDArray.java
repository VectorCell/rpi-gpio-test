import com.pi4j.io.gpio.*;

import java.io.*;
import java.util.*;

import java.nio.file.*;

public class LEDArray
{
	public static final Random RAND = new Random();

	public static final Pin[] PINS = {
		RaspiPin.GPIO_08,
		RaspiPin.GPIO_09,
		RaspiPin.GPIO_07,
		RaspiPin.GPIO_15,
		RaspiPin.GPIO_16,
		RaspiPin.GPIO_01,
		RaspiPin.GPIO_00,
		RaspiPin.GPIO_02,
		RaspiPin.GPIO_03,
		RaspiPin.GPIO_04,
		RaspiPin.GPIO_05,
		RaspiPin.GPIO_12,
		RaspiPin.GPIO_13,
		RaspiPin.GPIO_14,
		RaspiPin.GPIO_06,
		RaspiPin.GPIO_10
	};

	public static void main(String[] args)
	{
		// create gpio controller instance
		final GpioController gpio = GpioFactory.getInstance();

		GpioPinDigitalOutput[] leds = new GpioPinDigitalOutput[16];
		for (int k = 0; k < leds.length; ++k) {
			try {
				leds[k] = gpio.provisionDigitalOutputPin(PINS[k], "LED " + k, PinState.LOW);
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}

		for (int k = 0; k < leds.length; ++k) {
			leds[k].low();
		}

		System.out.println("Bouncing back and forth ...");
		int[] places = new int[2 * leds.length - 2];
		for (int k = 0; k < places.length; ++k) {
			if (k < leds.length) {
				places[k] = k;
			} else {
				places[k] = places.length - k;
			}
		}
		int prev = 0;
		for (int iter = 0; iter < 1; ++iter) {
			for (int k = 0; k < places.length; ++k) {
				leds[places[prev]].low();
				leds[places[k]].high();
				prev = k;
				sleep(50);
			}
		}

//		System.out.println("Blinking all LEDs ...");
//		boolean on = false;
//		for (int i = 0; i < 10; ++i) {
//			PinState state = on ? PinState.HIGH : PinState.LOW;
//			for (int k = 0; k < leds.length; ++k) {
//				leds[k].setState(state);
//			}
//			sleep(50);
//			on = !on;
//		}

//		String input = "This is a test string, that says hello to you. Hello!";
//		char[] chars = input.toCharArray();
//		for (int c = 0; c < chars.length; c += 2) {
//			String bin1 = Integer.toBinaryString(chars[c]);
//			String bin2 = c < chars.length - 1 ? Integer.toBinaryString(chars[c + 1]) : "00000000";
//			System.out.print(String.valueOf(chars[c]) + (c < chars.length - 1 ? String.valueOf(chars[c + 1]) : ""));
//			while (bin1.length() < leds.length / 2)
//				bin1 = "0" + bin1;
//			while (bin2.length() < leds.length / 2)
//				bin2 = "0" + bin2;
//			String bin = bin1 + bin2;
//			for (int k = 0; k < leds.length; ++k) {
//				if (bin.charAt(k) == '1')
//					leds[k].high();
//				else
//					leds[k].low();
//			}
//			sleep(50);
//		}
//		System.out.println();

//		System.out.println("Binary Counter ...");
//		for (int n = 0; n <= 0xFFFF; ++n) {
//			String bin = Integer.toBinaryString(n);
//			while (bin.length() < leds.length)
//				bin = "0" + bin;
//			bin = new StringBuilder(bin).reverse().toString();
//			for (int k = 0; k < leds.length; ++k) {
//				if (bin.charAt(k) == '1')
//					leds[k].high();
//				else
//					leds[k].low();
//			}
//			// System.out.printf("%s :: %d\n", new StringBuilder(bin).reverse().toString(), n);
//		}

		System.out.println("Random numbers ...");
		for (int i = 0; i < 100000; ++i) {
			String bin = Integer.toBinaryString(RAND.nextInt());
			while (bin.length() < 32)
				bin = "0" + bin;
			String bin1 = bin.substring(0, 16);
			String bin2 = bin.substring(16, 32);
			for (int k = 0; k < leds.length; ++k)
				leds[k].setState(bin1.charAt(k) == '0' ? PinState.LOW : PinState.HIGH);
			sleep(50);
			for (int k = 0; k < leds.length; ++k)
				leds[k].setState(bin2.charAt(k) == '0' ? PinState.LOW : PinState.HIGH);
			sleep(50);
		}

		System.out.println("Random wharbling ...");
		boolean[] states = new boolean[leds.length];
		for (int i = 0; i < 1000; ++i) {
			if (!states[0]) {
				states[0] = true;
			} else if (states[states.length - 1]) {
				states[states.length - 1] = false;
			} else {
				int top = 0;
				while (states[top])
					++top;
				if (RAND.nextBoolean()) {
					states[top] = true;
				} else {
					states[top - 1] = false;
				}
			}
			for (int k = 0; k < leds.length; ++k) {
				leds[k].setState(states[k] ? PinState.HIGH : PinState.LOW);
			}
			sleep(100);
		}

		for (int k = 0; k < leds.length; ++k)
			leds[k].low();
	}

	public static void sleep(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			System.err.println("WARNING: Thread.sleep interrupted");
		}
	}

	private static LinkedList<String> exec(String cmd)
	{
		LinkedList<String> output = new LinkedList<String>();
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			String s;

			BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(proc.getInputStream()));
			// read the output from the command
			while (true && (s = stdInput.readLine()) != null) {
				output.add(s);
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
		return output;
	}
}
