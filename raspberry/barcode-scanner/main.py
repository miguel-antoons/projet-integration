import time
import serial
from urllib.request import urlopen
import json
import requests
from datetime import datetime

url = "http://192.168.1.43:5000/api"
raspberry_id = 0
secret_key = ""

def main():
    global raspberry_id
    global secret_key

    try:
        id_file = open("raspberry_id", "r")
        raspberry_id = id_file.read()
        id_file.close()
        print(f"\n\033[92mRaspberry started with id {raspberry_id}\033[0m\n")

        key_file = open('raspberry_key', 'r')
        secret_key = key_file.read()
        key_file.close()
    except OSError:
        print("\033[91mRaspberry has not yet been registered\033[0m")

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

            except KeyboardInterrupt:
                # when Ctrl + C is entered on the keyboard, exit the loop and end the program
                print('\nExiting...')
                break

            except:
                # if ther is an error print an error message
                print("\033[93mNo data received from barcode-scanner\033[0m")

            # research barcode data if a pbarcode has been written from the scanner
            if len(barcode) > 0:
                try:
                    int(barcode)
                    process_barcode(barcode)
                    continue
                except:
                    try:
                        response = requests.get(barcode)
                        print("\033[93m\nThis is an URL\n\033[0m")
                    except:
                        print(f"\nTrying to add raspberry to user {barcode}\n")
                        add_to_user(barcode.replace('\r', ''))

    except KeyboardInterrupt:
        # when Ctrl + C is entered on the keyboard, exit the loop and end the program
        print('\nExiting...')


def process_barcode(barcode):
    print("\n--------------------------------------------")
    print(barcode)
    product_data = get_data_from_barcode(barcode)

    if product_data:
        product_data['secret'] = secret_key
        print(post_data(product_data))
        print_data(product_data)

    time.sleep(1)


def add_to_user(user):
    payload = {
        "user": user
    }

    response = requests.post(f"{url}/raspberry", json=payload)
    json_data = response.json()
    
    if json_data['status']:
        print(save_user(json_data['raspberry_id'], json_data['secret']))
    else:
        print("\033[93No user was found with provided qr-code\033[0m")


def save_user(new_id, new_key):
    global raspberry_id
    global secret_key

    raspberry_id = new_id
    secret_key = new_key
    id_file = open('raspberry_id', 'w')
    id_file.write(new_id)
    id_file.close()

    secret_file = open('raspberry_key', 'w')
    secret_file.write(new_key)
    secret_file.close()

    return f"\n\033[92mRaspberry has been registered with id {new_id}\033[0m\n"


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

        return post_data

    else:
        # if the product was not found, print an error message
        print("Product not found")
        return 0


def post_data(product_data):
    # /!\ to be replaced with correct ip / domain name /!\
    if product_data and raspberry_id:
        x = requests.post(f"{url}/food/{raspberry_id}", json=product_data)
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
