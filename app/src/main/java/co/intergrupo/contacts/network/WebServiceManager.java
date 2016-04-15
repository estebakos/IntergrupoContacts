package co.intergrupo.contacts.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.entity.Contact;
import co.intergrupo.contacts.interfaces.IWebService;

public class WebServiceManager {

    private final Context _context;
    private IWebService _IWebService;
    private RequestQueue _requestQueue;

    public WebServiceManager(Context context, IWebService listener) {
        _IWebService = listener;
        _context = context;
        _requestQueue = Volley.newRequestQueue(context);
    }

    private boolean isNetworkAvailable() {
        if (_context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

    public String getRestUrl() {
        return _context.getResources().getString(R.string.str_rest_server);
    }

    public void login(String username, String password) {
        try {
            if (!isNetworkAvailable()) {
                _IWebService.onInternetFail();
            } else {
                if (getRestUrl() != "") {

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRestUrl() + "users/login?username=" + username + "&password=" + password, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("response") == 1) {
                                    _IWebService.onLogin();
                                } else {
                                    _IWebService.onLoginProblem();
                                }
                            } catch (JSONException e) {
                                _IWebService.onLoginProblem();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getMessage();
                            _IWebService.onLoginProblem();
                        }
                    });

                    _requestQueue.add(request);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void getContacts() {
        try {
            if (!isNetworkAvailable()) {
                _IWebService.onInternetFail();
            } else {
                if (getRestUrl() != "") {

                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getRestUrl() + "contacts", new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<Contact> contacts = new ArrayList<>();
                            // HTTP
                            try {
                                Gson json = new Gson();
                                for (int i = 0; i < response.length(); i++) {
                                    Contact contact = new Contact();
                                    contact = json.fromJson(response.get(i).toString(), Contact.class);
                                    contacts.add(contact);
                                }
                                _IWebService.onContacts(contacts);

                            } catch (Exception error) {
                                error.printStackTrace();
                                _IWebService.onContactsProblem();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getMessage();
                            _IWebService.onContactsProblem();
                        }
                    });

                    _requestQueue.add(request);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getContactDetail(int id) {
        try {
            if (!isNetworkAvailable()) {
                _IWebService.onInternetFail();
            } else {
                if (getRestUrl() != "") {
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRestUrl() + "contacts/" + id, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Gson json = new Gson();
                                Contact contact = new Contact();
                                contact = json.fromJson(response.toString(), Contact.class);
                                _IWebService.onContactDetail(contact);
                            } catch (Exception e) {
                                _IWebService.onContactDetailProblem();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getMessage();
                            _IWebService.onContactDetailProblem();
                        }
                    });

                    _requestQueue.add(request);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
