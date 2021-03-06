package propya.mr.jeevan.Helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiCallHelpers {

    Context c;

    public ApiCallHelpers(Context c){
        this.c = c;
    }


    public void callVolley(String url, HashMap<String,Object> data,final CallbackVolley callbackVolley){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                callbackVolley::volleyCallBack,
                error -> {
            Log.i("Volley error",error.getLocalizedMessage());
            callbackVolley.volleyCallBack("Error occurred"+error.getLocalizedMessage());
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put("App-Id", InferMedica.inferMedicaKeys[0]);
                header.put("App-Key",InferMedica.inferMedicaKeys[1]);
                header.put("Content-Type","application/json");
                return header;
            }
        };
        request
                .setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(c).add(request);
        callbackVolley.volleyCallBack("Queued Request");
    }


    public interface CallbackVolley{
        void volleyCallBack(String msg);
        void volleyCallBack(JSONObject data);

    }

}
