import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

import java.text.DecimalFormat;

public class Input
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
		System.out.println("Initializing GPIO pins for input ...");

		final GpioController gpio = GpioFactory.getInstance();

		final GpioPinDigitalInput[] pins = new GpioPinDigitalInput[PINS.length];
		for (int k = 0; k < PINS.length; ++k) {
			pins[k] = gpio.provisionDigitalInputPin(PINS[k], "Pin " + TWO_DIGITS.format(k), PinPullResistance.PULL_DOWN);
		}
		final GpioPinDigitalInput clock = gpio.provisionDigitalInputPin(CLOCK_PIN, "Clock", PinPullResistance.PULL_DOWN);


		// create and register gpio pin listener for clock pin
		clock.addListener(new GpioPinListenerDigital() {
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//				System.out.printf("Clock change to %-4s :: ", event.getState());
//				for (int k = 0; k < pins.length; ++k) {
//					System.out.print(pins[k].isHigh() ? '1' : '0');
//				}
//				System.out.println();

				char[] charArray = new char[pins.length];
				for (int k = 0; k < pins.length; ++k) {
					charArray[k] = pins[k].isHigh() ? '1' : '0';
				}
				System.out.print((char)Integer.parseInt(String.valueOf(charArray), 2));
			}
		});

		// create and register gpio pin listener for data pins
//		for (final GpioPinDigitalInput pin : test) {
//			pin.addListener(new GpioPinListenerDigital() {
//				int previous = -1;
//				@Override
//				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//					// display pin state on console
//					// System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
//					int n = 0;
//					for (GpioPinDigitalInput pin : test) {
//						n *= 2;
//						if (pin.isHigh())
//							n += 1;
//					}
//					if (n != previous) {
//						String bin = Integer.toBinaryString(n);
//						while (bin.length() < test.length)
//							bin = "0" + bin;
//						System.out.printf("%2d :: %s\n", n, bin);
//					}
//					previous = n;
//				}
//			});
//		}
		
		System.out.println("Listening for input pin state changes ...");
		while (true) {
			sleep(10000);
		}
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
