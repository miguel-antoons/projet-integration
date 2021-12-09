# -*- coding: utf-8 -*-
# Your code goes below this line

# Flask Library
from flask import Blueprint, request, jsonify, json
from flask import Flask
from flask_bcrypt import Bcrypt
from flask_jwt_extended import jwt_required, get_jwt_identity
from flask_mail import Mail, Message

# Infos Database
from .database import  users
# Library
import random
import os
import uuid
import jwt
import datetime

from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)
mail = Mail(app)

# Mail Configuration

app.config['MAIL_SERVER'] = os.getenv('MAIL_SERVER')
app.config['MAIL_PORT'] = os.getenv('MAIL_PORT')
app.config['MAIL_USE_SSL'] = os.getenv('MAIL_USE_SSL')
app.config['MAIL_USERNAME'] = os.getenv('MAIL_USERNAME')
app.config['MAIL_PASSWORD'] = os.getenv('MAIL_PASSWORD')

mail = Mail(app)

getUsers = Blueprint('getUsers', __name__)

"""
*** API ROUTE FOR RESET PASSWORD  ***

 --- This route is the API to reset a user's password with his email address -- 

"""


@getUsers.route('/api/users/reset-password/checkemail', methods=['GET', 'PUT'])
def check_email():
    """[check_email]

    Args:
        email ([String]): [The user's email]

    Steps:

        1) Check the existence of the   
        2) Generates a random code of 6 numbers
        3) Adds the code in the database
        4)Sends an email with the code

    Returns:

       - [Data User]: [If the email exists the user information is returned]
       or
       - ['message': 'this email does not exist']: [If the email does not exist]

    """
    requete = request.json
    print(requete)

    email = requete['Email']
    print(email)


    if request.method == 'PUT':

        # Checks in the database if the email exists
        email_exist = list(users.find({"Email": email}))
        # print(email_exist)

        if email_exist:

            # generate a random code with 6 number
            numbers = random.sample(range(10), 6)
            number_str = ''.join(map(str, numbers))
            # print(number_str)

            # Update the code via email
            users.find_one_and_update(
                {"Email": email},
                {"$set":
                     {"Code": number_str}
                 }, upsert=True

            )

            # Send Mail

            msg = Message('Hello de SmartFridge Teams!', sender='smart.fridge.teams@gmail.com', recipients=[email])
            msg.body = "Bonjour de l'Equipe SmartFridge, voici votre code :  " + number_str
            mail.send(msg)

            # check ID
            for user in email_exist:
                user.pop('_id')

            # Email data : json.dumps(email_exist)

            return json.dumps({"message" : 'message: this email exist'})

        else:

            return json.dumps({"message" : 'this email does not exist' })
      
   
        


"""
*** API ROUTE FOR Check code  ***

 --- This route is the API to check code with his email address -- 

"""


@getUsers.route('/api/users/reset-password/checkcode', methods=['GET', 'PUT'])
def check_code_email():
    requete = request.json
    print(requete)

    email = requete['Email']
    print(email)
    code = requete['Code']
    if request.method == 'PUT':

        # Find code key by email
        check_good_code = list(users.find({"Email": email}, {"Code": 1}))
    
       

        # get good code from email
        good_code = check_good_code[0]['Code']
     

        if code == good_code:

            return json.dumps({"message" : 'The code is good'})

        else:

            return json.dumps({"message" : 'code is false'})


"""
 API ROUTE FOR UPDATE PASSWORD

 --- This route is the API to UPDATE PASSWORD with his email address -- 


"""

@getUsers.route('/api/users/update-password', methods=['GET','PUT'])
def update_password():
    requete = request.json
    print(requete)
    email = requete['Email']
    print(email)
    password = requete['Password']
    print(password)
    code = requete['Code']
    print(code)
 
    if request.method == 'PUT':
       
        #Check email address
        email_exist = list(users.find({"Email" : email,"Code" : code}))
        print(email_exist)

        if email_exist:

            #Update the code via email
            users.find_one_and_update(
            {"Email" : email},
            {"$set":
                {"Password": password}
            },upsert=True

                )


        return json.dumps(["Password Update is ok"])

    else:
        return json.dumps({"message" : "Password Update is refused Email / code False"})
        
        
        


       




# How work token

@getUsers.route('/api/users/email', methods=['GET'])
@jwt_required()
def email_exist():
    data = get_jwt_identity()
    print(data)
    result = list(users.find({"Email" : data["Email"]}))

    for client in result:
         client.pop('_id')
    
    print(result)
    return json.dumps(result[0])
