import sys
import board
import time
import busio
import board
import adafruit_tsl2591
import adafruit_dht
from urllib.request import urlopen
import json
import requests
import datetime
import csv

url = "https://smartfridge.online/api"

dhtDevice = adafruit_dht.DHT22(board.D4, use_pulseio=False)
i2c = busio.I2C(board.SCL, board.SDA)
sensor = adafruit_tsl2591.TSL2591(i2c)

old_temp=0
old_hum=0

#GET ID RASPBERRY
id_raspberry=""
while len(id_raspberry)==0:
    f = open("/home/pi/smart-fridge/barcode-scanner/raspberry_id", "r")
    id_raspberry = f.read()

while True:
    try:
        #GET CAPTORS DATA
        lux = round(sensor.lux, 2)
        humidity = dhtDevice.humidity
        temperature = dhtDevice.temperature
        date = datetime.datetime.now()
        #print(date)
        #print(lux)
        #print(humidity)
        #print(temperature)
        print("Temperature: {:.1f}°C / Humidite: {}% / Niveau de lumiere: {}lux/ Date actuel: {}".format(temperature,humidity,lux,date))
        #GET ID RASPBERRY
        f = open("/home/pi/smart-fridge/barcode-scanner/raspberry_id", "r")
        id_raspberry = f.read()
        #print(id_raspberry)
        #print(type(id_raspberry))
        print(id_raspberry)
        #If the current temperature is 1 lower or higher than the CSV temperature which is average
        if float(temperature) < float(old_temp) - 1 or float(humidity) < float(old_hum) - 5.0:
            old_temp=temperature
            old_hum=humidity
            print("Température diminue")


            #TEST PUT METHODE
            data_put  ={
                "ID_raspberry":id_raspberry,
                "Light":lux,
                "Humidity":humidity,
                "Temperature":temperature,
                "Date":str(date)
            }
            r = requests.put('https://smartfridge.online/api/environnement/temhumlight',json=data_put)
            print(r.content)
            print(r.status_code)


        elif float(temperature) > float(old_temp) + 1 or float(humidity) > float(old_hum) + 5.0:
            old_temp=temperature
            old_hum=humidity
            print("Température augmente")


            #TEST PUT METHODE
            data_put  ={
                "ID_raspberry":id_raspberry,
                "Light":lux,
                "Humidity":humidity,
                "Temperature":temperature,
                "Date":str(date)
            }
            r = requests.put('https://smartfridge.online/api/environnement/temhumlight',json=data_put)
            print(r.content)
            print(r.status_code)

        else:
            print("Pas de grands changement")

    except RuntimeError as error:
        #print(error.args[0])
        continue

    time.sleep(2.0)
