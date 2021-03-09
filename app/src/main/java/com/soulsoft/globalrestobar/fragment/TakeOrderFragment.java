package com.soulsoft.globalrestobar.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.soulsoft.globalrestobar.BaseFragment;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.adapter.GeTableAdapter1;
import com.soulsoft.globalrestobar.adapter.GetMenuAdapter;
import com.soulsoft.globalrestobar.adapter.ServesInSpinnerAdapter;
import com.soulsoft.globalrestobar.adapter.TakeOrderAdapter;
import com.soulsoft.globalrestobar.model.TakeMenuOrder;
import com.soulsoft.globalrestobar.model.itemunit.ItemUnitResponse;
import com.soulsoft.globalrestobar.model.menucard.MenuDataBO;
import com.soulsoft.globalrestobar.model.table.GetTableDataBO;
import com.soulsoft.globalrestobar.utility.AllKeys;
import com.soulsoft.globalrestobar.utility.CommonMethods;
import com.soulsoft.globalrestobar.utility.ConfigUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import io.paperdb.Paper;

public class TakeOrderFragment extends BaseFragment implements View.OnClickListener, TakeOrderAdapter.TakeOrderListener {

    @BindView(R.id.act_tableno)
    AutoCompleteTextView actTableNo;
    @BindView(R.id.act_menu)
    AutoCompleteTextView actMenu;
    @BindView(R.id.spin_ServesIn)
    Spinner acsSpinServesIn;
    @BindView(R.id.et_rate)
    TextInputEditText etRate;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.et_qty)
    EditText etQuantity;
    @BindView(R.id.btn_clear)
    AppCompatButton btnClear;
    @BindView(R.id.btn_add)
    AppCompatButton btnAdd;
    @BindView(R.id.btn_exit)
    AppCompatButton btnExit;
    @BindView(R.id.btn_takeorder)
    AppCompatButton btnTakeOrder;
    @BindView(R.id.et_splrequest)
    EditText etSpecialRequest;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    private final ArrayList<MenuDataBO> menuDataBOArrayList = new ArrayList<>();
    private final ArrayList<GetTableDataBO> getTableDataBOArrayList = new ArrayList<>();
    private MenuDataBO menuDataBO = null;
    private GetTableDataBO getTableDataBO = null;
    private static final String TAG = "TakeOrderFragment";
    private ItemUnitResponse itemUnitResponse;
    private String stServesInId, stSectionId, stGoodsCode, stServesInName, stOrderId, stItemType;
    private double stRate;
    private float sum = 0.0f, total;
    private final ArrayList<TakeMenuOrder> takeOrderArrayList=new ArrayList<>();
    private TakeMenuOrder takeMenuOrder;

    public TakeOrderFragment( ) {
        // Required empty public constructor
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.fragment_take_order;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //basic intialisation...
        initViews();

        //attach data to table and menu
        setData();
    }

    private void initViews( ) {
        btnClear.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnTakeOrder.setOnClickListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed( ) {
                // Handle the back button event
                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to exit application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                getActivity().finish();
                                //close();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                //clear all fields....
                clearFields();
                break;

            case R.id.btn_add:
                if (isValidatedTable()) {
                    addFood();
                }
                for (int i = 0; i < takeOrderArrayList.size(); i++) {
                    Log.e(TAG, "onClick: " + takeOrderArrayList.get(i).getTotal());
                    total = Float.parseFloat((takeOrderArrayList.get(i).getTotal()));
                }
                sum += total;
                btnTakeOrder.setText("Total: "+sum);
                // sum has the value for the food menu total.
                break;

            case R.id.btn_exit:
                break;

            case R.id.btn_takeorder:
                break;
        }
    }

    private void addFood( ) {
        takeMenuOrder = new TakeMenuOrder(stGoodsCode, actMenu.getText().toString(), stItemType, stServesInId, etRate.getText().toString(),
                etSpecialRequest.getText().toString(), etQuantity.getText().toString(), etAmount.getText().toString(), stServesInName);

        takeOrderArrayList.add(takeMenuOrder);

        TakeOrderAdapter takeOrderAdapter = new TakeOrderAdapter(getActivity().getBaseContext(), takeOrderArrayList, this);
        takeOrderAdapter.notifyDataSetChanged();
        rvMenu.setAdapter(takeOrderAdapter);
        // llSpecialRequest.setVisibility(View.GONE);

        //clear all fields....
        clearFields();
    }

    private void setData( ) {
        if (Paper.book().read("menucard") != null) {
            menuDataBOArrayList.addAll(Paper.book().read("menucard"));
            GetMenuAdapter getMenuAdapter = new GetMenuAdapter(mContext, menuDataBOArrayList);
            actMenu.setAdapter(getMenuAdapter);
            actMenu.setThreshold(1);
        } else {
            showDialogWindow("Error", "Kindly login again!");
        }

        if (Paper.book().read("tabledata") != null) {
            getTableDataBOArrayList.addAll(Paper.book().read("tabledata"));
            GeTableAdapter1 getTableAdapter = new GeTableAdapter1(mContext, getTableDataBOArrayList);
            actTableNo.setAdapter(getTableAdapter);
            actTableNo.setThreshold(1);
        } else {
            showDialogWindow("Error", "Kindly login again!");
        }

        actMenu.setOnItemClickListener((adapterView, view, i, l) -> {
            //Log.e(TAG, "onItemClick: actownername " + adapterView.getItemAtPosition(i));
            menuDataBO = (MenuDataBO) adapterView.getItemAtPosition(i);
            stGoodsCode = menuDataBO.getITEMCODE();
            Log.e(TAG, "onItemClick:menuDataBO " + menuDataBO);
            etQuantity.setText("1");
            if (!stGoodsCode.isEmpty()) {
                loadServesInData();
            }

            //As quantity input amount will be displayed
            calculateAmount();
        });

        actTableNo.setOnItemClickListener((adapterView, view, i, l) -> {
            //Log.e(TAG, "onItemClick: actownername " + adapterView.getItemAtPosition(i));
            getTableDataBO = (GetTableDataBO) adapterView.getItemAtPosition(i);
            Log.e(TAG, "onItemClick: getTableData " + getTableDataBO);
            stSectionId = getTableDataBO.getSID();
        });
    }

    //load ServesIndata like Plate,180ml, 90ml etc
    public void loadServesInData( ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonMethods.getPrefrence(mContext, AllKeys.BASE_URL).concat(ConfigUrl.ITEM_UNITCARD), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                Gson gson = new Gson();
                itemUnitResponse = gson.fromJson(response, ItemUnitResponse.class);
                if (itemUnitResponse.getTable().size() != 0) {
                    ServesInSpinnerAdapter servesInSpinnerAdapter = new ServesInSpinnerAdapter(getContext(), itemUnitResponse.getTable());
                    acsSpinServesIn.setAdapter(servesInSpinnerAdapter);
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> Log.e(TAG, "onErrorResponse: " + error)) {
            protected Map<String, String> getParams( ) throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("SectionId", stSectionId);
                params.put("GoodsCode", stGoodsCode);

                Log.e(TAG, "getParams: " + params);
                return params;
            }
        };
        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        mQueue.add(stringRequest);

        acsSpinServesIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stServesInId = itemUnitResponse.getTable().get(position).getUNITID();
                stServesInName = itemUnitResponse.getTable().get(position).getUNITNAME();
                stRate = Double.parseDouble(itemUnitResponse.getTable().get(position).getRATE());

                etRate.setText(String.valueOf(stRate));

                try {

                    if (Objects.requireNonNull(etQuantity.getText()).toString().length() == 0) {
                        Objects.requireNonNull(etAmount.getText()).clear();
                    } else {
                        double result = ((Integer.parseInt(etQuantity.getText().toString()) * stRate));
                        etAmount.setText(String.valueOf(result));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //As quantity input amount will be displayed
    private void calculateAmount( ) {
        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (etQuantity.getText().toString().length() == 0) {
                        etAmount.getText().clear();
                    } else {
                        double result = ((Integer.parseInt(etQuantity.getText().toString()) * stRate));
                        //Toast.makeText(getContext(), "result" + result, Toast.LENGTH_SHORT).show();
                        etAmount.setText(String.valueOf(result));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showDialogWindow(String title, String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) mContext)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        (dialogInterface, which) -> {
                            // Delete Operation
                            dialogInterface.dismiss();
                        })
                //.setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss())
                .build();

        // Show Dialog
        mDialog.show();
    }

    //validations....
    public boolean isValidatedTable( ) {
        if (actTableNo.getText().toString().isEmpty()) {
            showDialogWindow("Error", "Please select table!");
            return false;
        }

        if (actMenu.getText().toString().isEmpty()) {
            showDialogWindow("Error", "Please select menu!");
            return false;
        }

        if (etQuantity.getText().toString().isEmpty()) {
            showDialogWindow("Error", "Quantity required!");
            return false;
        }

        if (etRate.getText().toString().isEmpty()) {
            showDialogWindow("Error", "Rate cannot be empty!");
            return false;
        }

        if (etAmount.getText().toString().isEmpty()) {
            showDialogWindow("Error", "Amount cannot be empty!");
            return false;
        }
        return true;
    }


    //clear all fields....
    private void clearFields( ) {
        acsSpinServesIn.setAdapter(null);
        actMenu.getText().clear();
        etQuantity.getText().clear();
        etRate.getText().clear();
        etAmount.getText().clear();
        etSpecialRequest.getText().clear();
    }

    @Override
    public void onTakeOrderClick(int position) {

    }
}