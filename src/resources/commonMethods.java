package resources;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class commonMethods {
    public static JsonPath rawToJson(Response response){
        return new JsonPath(response.asString());

    }

    public static XmlPath rawToXml(Response response){
        return new XmlPath(response.asString());

    }


}
