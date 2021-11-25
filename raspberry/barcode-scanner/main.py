import time
import serial
from urllib.request import urlopen
import json


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
            try:
                # read line and convert to ascii
                barcode = str(ser.readline().decode('ascii'))

                # research barcode data if a pbarcode has been written from the scanner
                if len(barcode) > 0:
                    print("\n--------------------------------------------")
                    print(barcode)
                    get_data_from_barcode(barcode)
            except:
                # if ther is an error print an error message
                print("No data received from barcode-scanner")

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
        # if the product status is ste to 1 (--> which means the product was found)
        # print the information of the product
        # (in the future, an api call to store the following data elsewhere can be added here)
        print(f"\033[1m{json_data['product']['product_name_fr']} \033[0m \n")
        print(f"BRAND : {json_data['product']['brands']} \n")
        print(f"QUANTITY : {json_data['product']['quantity']} \n")
        print(f"NUTRISCORE : {json_data['product']['nutriscore_grade']} \n")
        print(f"ECOSCORE : {json_data['product']['ecoscore_grade']} \n")
        print(f"INGREDIENTS : {json_data['product']['ingredients_text_fr']} \n")
        print(f"NUTRIMENTS : {json_data['product']['nutriments']} \n")

    else:
        # if the product was not found, print an error message
        print("Product not found")


if __name__ == "__main__":
        main()
