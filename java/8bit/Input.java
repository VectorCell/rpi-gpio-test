import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

public class Input
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
		System.out.println("Initializing GPIO pins for input ...");
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalInput[] pins = new GpioPinDigitalInput[PINS_DATA.length];
		for (int k = 0; k < PINS_DATA.length; ++k) {
			pins[k] = gpio.provisionDigitalInputPin(PINS_DATA[k], "DATA_" + k, PinPullResistance.PULL_DOWN);
		}
		final GpioPinDigitalInput readReady = gpio.provisionDigitalInputPin(PIN_READ_READY, "READ_READY", PinPullResistance.PULL_DOWN);
		final GpioPinDigitalOutput readConfirm = gpio.provisionDigitalOutputPin(PIN_READ_CONFIRM, "READ_CONFIRM", PinState.LOW);

		// create and register gpio pin listener for read ready pin
		readReady.addListener(new GpioPinListenerDigital() {
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//				String bin = "";
//				for (int k = 0; k < pins.length; ++k) {
//					bin += pins[k].isHigh() ? "1" : "0";
//				}
//				System.out.printf("%s :: %d\n", bin, Integer.parseInt(bin, 2));
				// sleep(50);
				readConfirm.setState(event.getState());
			}
		});
		
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
