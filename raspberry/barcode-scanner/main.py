import time
import serial
from urllib.request import urlopen
import json
import requests
from datetime import datetime


def main():
    # connect to barcode-scanner port 
    ser = serial.Serial(
            "/dev/ttyS0",
            baudrate=9600,
            timeout=0.5
    )

    try:
        # infinite loop to read barcodes and research product data
        while True:
            product_data = {}

            try:
                # read line and convert to ascii
                barcode = str(ser.readline().decode('ascii'))

            except:
                # if ther is an error print an error message
                print("No data received from barcode-scanner")

            # research barcode data if a pbarcode has been written from the scanner
            if len(barcode) > 0:
                print("\n--------------------------------------------")
                print(barcode)
                product_data = get_data_from_barcode(barcode)
                print(post_data(product_data))
                print_data(product_data)

            time.sleep(1)

    except KeyboardInterrupt:
        # when Ctrl + C is entered on the keyboard, exit the loop and end the program
        print('Exiting...')


# this function gets data from a product with help from the product's barcode
def get_data_from_barcode(barcode):
    # prepare api link with correct barcode
    api_url = f"https://world.openfoodfacts.org/api/v0/product/{barcode}"

    # call the api
    response = urlopen(api_url)

    # convert the json to a python dictionary
    json_data = json.loads(response.read())

    if json_data['status']:
        post_data = {}

        # if the product status is ste to 1 (--> which means the product was found)
        # print the information of the product
        # (in the future, an api call to store the following data elsewhere can be added here)
        post_data["Nom"] = json_data['product']['product_name_fr']

        # for every key we need of the product, verify if the key exists and print the data of that key
        try:
            post_data["Marque"] = json_data['product']['brands']
        except KeyError:
            post_data["Marque"] = "/"

        try:
            post_data["Quantite"] = json_data['product']['quantity']
        except KeyError:
            post_data["Quantite"] = "/"

        try:
            post_data["Nutriscore"] = json_data['product']['nutriscore_grade']
        except KeyError:
            post_data["Nutriscore"] = "/"

        try:
            post_data["Ecoscore"] = json_data['product']['ecoscore_grade']
        except KeyError:
            post_data["Ecoscore"] = "/"

        try:
            post_data["Ingredients"] = json_data['product']['ingredients_text_fr']
        except KeyError:
            post_data["Ingredients"] = "/"
        
        try:
            post_data["Valeurs"] = json_data['product']['nutriments']
        except KeyError:
            post_data["Valeurs"] = "/"

        post_data["Date"] = datetime.today().strftime("%d/%m/%Y")
        post_data["Categorie"] = "Autre"
        post_data["Utilisateur"] = "me"
        post_data["Lieu"] = "Frigo"
        post_data["Poids"] = "/"

        return post_data

    else:
        # if the product was not found, print an error message
        print("Product not found")
        return 0


def post_data(product_data):
    # /!\ to be replaced with correct ip / domain name /!\
    if product_data:
        x = requests.post("http://192.168.1.44:5000/api/addFood", json=product_data)
        return x

    return


def print_data(product_data):
    print(f"\033[1m{product_data['Nom']} \033[0m \n")
    print(f"BRAND : {product_data['Marque']} \n")
    print(f"QUANTITY : {product_data['Quantite']} \n")
    print(f"NUTRISCORE : {product_data['Nutriscore']} \n")
    print(f"ECOSCORE : {product_data['Ecoscore']} \n")
    print(f"INGREDIENTS : {product_data['Ingredients']} \n")
    print(f"NUTRIMENTS : {product_data['Valeurs']} \n")


if __name__ == "__main__":
        main()
