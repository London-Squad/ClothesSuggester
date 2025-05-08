package data.weather.service.utils

object DummyWeatherResponseData {
    val TODAY_WEATHER = mapOf(
        "success" to """{"latitude":52.52,"longitude":13.439999,"generationtime_ms":0.0503063201904297,"utc_offset_seconds":0,"timezone":"GMT","timezone_abbreviation":"GMT","elevation":42,"current_weather_units":{"time":"iso8601","interval":"seconds","temperature":"°C","windspeed":"km/h","winddirection":"°","is_day":"","weathercode":"wmo code"},"current_weather":{"time":"2025-05-08T05:30","interval":900,"temperature":7.2,"windspeed":5.2,"winddirection":78,"is_day":1,"weathercode":2}}""",
        "failed" to """{"error":true,"reason":"Test"}"""
    )
}