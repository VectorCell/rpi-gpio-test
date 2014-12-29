import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)

gpio_pins = [7, 11, 12, 16]

GPIO.setup(gpio_pins, GPIO.IN)

while True:
	gpio_status = map(GPIO.input, gpio_pins)
	print gpio_status
	time.sleep(1)

GPIO.cleanup()
