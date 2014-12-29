import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

public class Input
{
	public static void main(String[] args)
	{
		final GpioController gpio = GpioFactory.getInstance();

		GpioPinDigitalInput test1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "Test 1", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "Test 2", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Test 3", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Test 4", PinPullResistance.PULL_DOWN);
		final GpioPinDigitalInput[] test = {test1, test2, test3, test4};

		// create and register gpio pin listener
		for (final GpioPinDigitalInput pin : test) {
			pin.addListener(new GpioPinListenerDigital() {
				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
					// display pin state on console
					// System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
					for (GpioPinDigitalInput pin : test) {
						if (pin.isHigh()) {
							System.out.print("0");
						} else {
							System.out.print(" ");
						}
					}
					System.out.println();
				}
			});
		}
		sleep(10000);

		/*
		// create gpio controller instance
		final GpioController gpio = GpioFactory.getInstance();

		GpioPinDigitalInput test1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "Test 1", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "Test 2", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Test 3", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput test4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Test 4", PinPullResistance.PULL_DOWN);
		GpioPinDigitalInput[] test = {test1, test2, test3, test4};

		while (true) {
			for (GpioPinDigitalInput pin : test) {
				if (pin.isHigh()) {
					System.out.print("0");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
			sleep(1000);
		}
		*/
	}

	/*
	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		// display pin state on console
		System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
	}
	*/

	public static void sleep(long millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			System.err.println("WARNING: Thread.sleep interrupted");
		}
	}
}
