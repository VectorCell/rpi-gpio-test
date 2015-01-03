import com.pi4j.io.gpio.*;

public class Output
{
	public static final Pin PIN_READ_READY = RaspiPin.GPIO_03;
	public static final Pin PIN_READ_CONFIRM = RaspiPin.GPIO_04;
	public static final Pin[] PINS_DATA = {
		RaspiPin.GPIO_08,
		RaspiPin.GPIO_09,
		RaspiPin.GPIO_07,
		RaspiPin.GPIO_15,
		RaspiPin.GPIO_16,
		RaspiPin.GPIO_01,
		RaspiPin.GPIO_00,
		RaspiPin.GPIO_02
	};

	public static void main(String[] args)
	{
		System.out.print("Initializing GPIO pins for output ... ");
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput[] pins = new GpioPinDigitalOutput[PINS_DATA.length];
		for (int k = 0; k < pins.length; ++k) {
			System.out.print("p" + k + " ");
			pins[k] = gpio.provisionDigitalOutputPin(PINS_DATA[k], "DATA_" + k, PinState.LOW);
		}
		System.out.print("read_ready ");
		final GpioPinDigitalOutput readReady = gpio.provisionDigitalOutputPin(PIN_READ_READY, "READ_READY", PinState.LOW);
		System.out.print("read_confirm ");
		final GpioPinDigitalInput readConfirm = gpio.provisionDigitalInputPin(PIN_READ_CONFIRM, "READ_CONFIRM", PinPullResistance.PULL_DOWN);
		System.out.println();

		System.out.println("Sending all byte values, 0-255, sequentially ...");
		long startTime = System.currentTimeMillis();
		int numBytes = 0;
		for (int i = 0; i < 256; ++i) {
			System.out.printf("Sending 256 byte block %3d/256 ... \n", i + 1);
			for (int num = 0; num < 256; ++num) {
				String bin = Integer.toBinaryString(num);
				while (bin.length() < pins.length)
					bin = "0" + bin;
				for (int k = 0; k < pins.length; ++k)
					pins[k].setState(bin.charAt(k) == '0' ? PinState.LOW : PinState.HIGH);
				toggle(readReady);
				System.out.printf("Sending 256 byte block %3d/256 :: ", i + 1);
				System.out.printf("Sent %3d, waiting for confirmation ... ", num);
				while (readReady.getState() != readConfirm.getState());
				System.out.println("confirmed.");
				++numBytes;
			}
		}
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		double dataRate = numBytes / (elapsed / 1000.0);
		System.out.printf("Done in %ld ms, at $f Bps\n", elapsed, dataRate);
	}

	public static void toggle(GpioPinDigitalOutput pin)
	{
		if (pin.getState() == PinState.HIGH)
			pin.setState(PinState.LOW);
		else
			pin.setState(PinState.HIGH);
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
