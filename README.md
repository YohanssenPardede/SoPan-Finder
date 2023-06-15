# ML-API-Rizky
Based on ML API Template from Kaenova(big shoutout), this API is loaded with SoPan ML Model for prediction purpose. Carefully read the comment on the code as they give better insight on what and how the API function.
I didn't clean all the comments and function but it serve a great reference for learning purpose.

## Prediction for CC
The endpoints for prediction is `/predict_text`
Based on the tensor shape model (None, 1, 8), the JSON payload should look like this:
```JSON
{
  "date":int,
  "pv_demand": float,
  "temp": float,
  "wind_speed": float,
  "rain_1h": float,
  "snow_1h": float,
  "clouds_all": float,
  "percip_1h": float
}
```
