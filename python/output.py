import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)

gpio_pins = [7, 11, 12, 13, 15, 16, 18, 19, 21, 22, 23, 24, 26, 29, 31, 32, 33, 35, 36, 37, 38, 40]

GPIO.setup(gpio_pins, GPIO.OUT)

pin_index = 0

while True:
	pin_index += 1
	if pin_index >= len(gpio_pins):
		pin_index = 0
	pin = gpio_pins[pin_index]
	print "PIN ON"
	GPIO.output(pin, 1)
	time.sleep(1)
	print "PIN OFF"
	GPIO.output(pin, 0)
	time.sleep(1)

GPIO.cleanup()
