from pymongo import MongoClient

# in this file, the database configuration will be done

# this is a temporar configuration and will change over time
client = MongoClient("mongodb+srv://alexis:sixela@clusteralexis.7q0fj.mongodb.net/test")
db = client.get_database('SmartFridge')
