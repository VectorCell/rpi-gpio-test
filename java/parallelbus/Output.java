import com.pi4j.io.gpio.*;

import java.text.DecimalFormat;

public class Output
{
	public static final DecimalFormat TWO_DIGITS = new DecimalFormat("00");

	public static final Pin CLOCK_PIN = RaspiPin.GPIO_11;
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
		System.out.println("Initializing GPIO pins for output ...");
		final GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput[] pins = new GpioPinDigitalOutput[PINS.length];
		for (int k = 0; k < PINS.length; ++k) {
			pins[k] = gpio.provisionDigitalOutputPin(PINS[k], "Pin " + TWO_DIGITS.format(k), PinState.LOW);
		}
		GpioPinDigitalOutput clock = gpio.provisionDigitalOutputPin(CLOCK_PIN, "Clock", PinState.LOW);

		boolean clock_on = false;

//		System.out.println("Blinking all bins with clock ...");
//		clock_on = false;
//		for (int i = 0; i < 10; ++i) {
//			for (int k = 0; k < pins.length; ++k) {
//				pins[k].setState(clock_on ? PinState.HIGH : PinState.LOW);
//			}
//			clock.setState(clock_on ? PinState.HIGH : PinState.LOW);
//			sleep(500);
//			clock_on = !clock_on;
//		}

		String msg = "\0\0\0Hello ENIAC, this is PiOne. Do you read me? The current time is " + System.currentTimeMillis() + ".\n";
		System.out.println("Sending message [" + msg.substring(0, msg.length() - 1) + "] ...");
		clock_on = false;
		for (char ch : msg.toCharArray()) {
			String bin = Integer.toBinaryString(ch);
			while (bin.length() < pins.length)
				bin = "0" + bin;
			// bin = new StringBuilder(bin).reverse().toString();
			for (int k = 0; k < pins.length; ++k) {
				pins[k].setState(bin.charAt(k) == '0' ? PinState.LOW : PinState.HIGH);
			}
			clock.setState(clock_on ? PinState.HIGH : PinState.LOW);
			sleep(5);
			clock_on = !clock_on;
		}

		for (int i = 0; i < 32; ++i) {
			msg = "\0\0\0Hello ENIAC, this is PiOne. Do you read me? The current time is " + System.currentTimeMillis() + ".\n";
			System.out.println("Sending message [" + msg.substring(0, msg.length() - 1) + "] ...");
			clock_on = false;
			for (char ch : msg.toCharArray()) {
				String bin = Integer.toBinaryString(ch);
				while (bin.length() < pins.length)
					bin = "0" + bin;
				// bin = new StringBuilder(bin).reverse().toString();
				for (int k = 0; k < pins.length; ++k) {
					pins[k].setState(bin.charAt(k) == '0' ? PinState.LOW : PinState.HIGH);
				}
				clock.setState(clock_on ? PinState.HIGH : PinState.LOW);
				sleep(5);
				clock_on = !clock_on;
			}
			sleep(100);
		}

//		int current = 0;
//		int prev = test.length - 1;
//		for (int k = 0; k < test.length * 8; ++k) {
//			test[current].high();
//			test[prev].low();
//			current = ++current >= test.length ? 0 : current;
//			prev = ++prev >= test.length ? 0 : prev;
//			sleep(50);
//		}
//
//		for (GpioPinDigitalOutput pin : test) {
//			test.low();
//		}

//		for (int j = 0; j < 4; ++j) {
//			boolean[] prev = new boolean[test.length];
//			boolean[] curr = new boolean[test.length];
//			for (int n = 0; n < 16; ++n) {
//				String bin = Integer.toBinaryString(n);
//				while (bin.length() < test.length) {
//					bin = "0" + bin;
//				}
//				System.out.printf("%2d :: %s\n", n, bin);
//				for (int k = 0; k < curr.length; ++k) {
//					curr[k] = bin.charAt(k) == '1';
//					if (prev[k] != curr[k]) {
//						if (curr[k])
//							test[k].high();
//						else
//							test[k].low();
//					}
//					prev[k] = curr[k];
//				}
//				sleep(1000);
//			}
//		}
	}

	public static void sleep(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			System.err.println("WARNING: Thread.sleep interrupted");
		}
	}
}
