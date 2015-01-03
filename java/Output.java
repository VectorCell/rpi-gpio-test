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

		/*
		int current = 0;
		int prev = test.length - 1;
		for (int k = 0; k < test.length * 8; ++k) {
			test[current].high();
			test[prev].low();
			current = ++current >= test.length ? 0 : current;
			prev = ++prev >= test.length ? 0 : prev;
			sleep(50);
		}

		for (GpioPinDigitalOutput pin : test) {
			test.low();
		}
		*/

		for (int j = 0; j < 4; ++j) {
			boolean[] prev = new boolean[test.length];
			boolean[] curr = new boolean[test.length];
			for (int n = 0; n < 16; ++n) {
				String bin = Integer.toBinaryString(n);
				while (bin.length() < test.length) {
					bin = "0" + bin;
				}
				System.out.printf("%2d :: %s\n", n, bin);
				for (int k = 0; k < curr.length; ++k) {
					curr[k] = bin.charAt(k) == '1';
					if (prev[k] != curr[k]) {
						if (curr[k])
							test[k].high();
						else
							test[k].low();
					}
					prev[k] = curr[k];
				}
				sleep(1000);
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
