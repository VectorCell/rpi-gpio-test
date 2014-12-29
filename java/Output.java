import com.pi4j.io.gpio.*;

public class Output
{
	public static void main(String[] args)
	{
		// create gpio controller instance
		final GpioController gpio = GpioFactory.getInstance();

		GpioPinDigitalOutput test1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "Test 1", PinState.LOW);
		GpioPinDigitalOutput test2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Test 2", PinState.LOW);
		GpioPinDigitalOutput test3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Test 3", PinState.LOW);
		GpioPinDigitalOutput test4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Test 4", PinState.LOW);
		GpioPinDigitalOutput[] test = {test1, test2, test3, test4};

		int current = 0;
		int prev = test.length - 1;
		for (int k = 0; k < test.length * 8; ++k) {
			test[current].high();
			test[prev].low();
			current = ++current >= test.length ? 0 : current;
			prev = ++prev >= test.length ? 0 : prev;
			sleep(50);
		}

		for (int k = 0; k < 2; k++) {
			for (GpioPinDigitalOutput pin : test) {
				pin.high();
			}
			sleep(1000);
			for (GpioPinDigitalOutput pin : test) {
				pin.low();
			}
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
