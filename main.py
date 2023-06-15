import os
import uvicorn
import traceback
import tensorflow as tf

from pydantic import BaseModel
from urllib.request import Request
from fastapi import FastAPI, Response, UploadFile
from utils import load_image_into_numpy_array
from typing import List


model = tf.saved_model.load("./my_model")



app = FastAPI()

# This endpoint is for a test (or health check) to this server
@app.get("/")
def index():
    return "Hello world from ML endpoint!"

class RequestText(BaseModel):
    date: List[str]
    pv_demand: List[float]
    temp: List[float]
    wind_speed: List[float]
    rain_1h: List[float]
    snow_1h: List[float]
    clouds_all: List[float]
    percip_1h: List[float]




def convert_date(date_array):
    date_string = date_array[0]
    date_parts = date_string.split('T')[0].split('-')
    year = int(date_parts[0])
    month = int(date_parts[1])
    day = int(date_parts[2])
    return year * 10000 + month * 100 + day



@app.post("/predict_text")
def predict_text(req: RequestText, response: Response):
    try:

        data_date = convert_date(req.date)
        data_pv_demand = req.pv_demand[0]
        data_temp = req.temp[0]
        data_wind_speed = req.wind_speed[0]
        data_rain_1h = req.rain_1h[0]
        data_snow_1h = req.snow_1h[0]
        data_clouds_all = req.clouds_all[0]
        data_percip_1h = req.percip_1h[0]


  
        result = model([[[data_date, data_pv_demand, data_temp, data_wind_speed, data_rain_1h, data_snow_1h, data_clouds_all, data_percip_1h]]])
       
        if(result > 0.5):
            result="PV Power is high"
            name_sopan="Solarcell PV Maysun 400WP Mono Shingled",
            panel_specification = {
                'solar_cell_type': 'Monocrystalline',
                'power_output': 'up to 400 W',
                'efficiency': '20,48%',
                'dimensions': '1722 x 1134 x 30 mm',
                'weight': '22Kg',
            }
            link="https://tokopedia.link/i9K137VozAb"
            link_img="https://storage.googleapis.com/sopan/imgs/mono.png"
        elif(result < 0.5):
            result="PV Power is low"
            name_sopan="Mono Panel Surya 100 WP Monocrystaline"
            panel_specification = {
                'solar_cell_type': 'Monocrytalline',
                'power_output': '100 W',
                'efficiency': '16,93%',
                'dimensions': '910 x 680 x 30mm',
                'weight': '10 Kg',
            }
            link="https://tokopedia.link/jfCU4wTozAb"
            link_img="https://storage.googleapis.com/sopan/imgs/thin-film%20sopan.png"
        else:
            result="PV Power is medium"
            name_sopan="Solar Panel 250Wp Polycrystalline"
            panel_specification = {
                'solar_cell_type': 'Polycrystalline',
                'power_output': '250 W',
                'efficiency': '16.93%',
                'dimensions': '700x540x30mm',
                'weight': '12 Kg',
            }
            link="https://tokopedia.link/BIUmDnOozAb"
            link_img="https://storage.googleapis.com/sopan/imgs/poly%20sopan.png"

        return {
            'result': result,
            'name_sopan': name_sopan,
            'panel_specification': panel_specification,
            'link': link,
            'link_img': link_img,
        }


        return "Endpoint not implemented"
    except Exception as e:
        traceback.print_exc()
        response.status_code = 500
        return "Internal Server Error"


# Starting the server
# Your can check the API documentation easily using /docs after the server is running
port = os.environ.get("PORT", 8080)
print(f"Listening to http://0.0.0.0:{port}")
uvicorn.run(app, host='0.0.0.0',port=port)