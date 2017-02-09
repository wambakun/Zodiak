package developerkampus.zodiak;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import developerkampus.zodiak.activity.DetailActivity;
import developerkampus.zodiak.model.ZodiakModel;
import developerkampus.zodiak.model.ZodiakParcel;
import developerkampus.zodiak.retrofit.ApiInterface;
import developerkampus.zodiak.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.tillName)
    TextInputLayout tillName;
    @BindView(R.id.tillDate)
    TextInputLayout tillDate;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.pb_loading)
    ProgressBar pb_loading;
    @BindView(R.id.lin_loading)
    LinearLayout lin_loading;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pb_loading.setAlpha(0.0f);
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            MainActivity.this,
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)
                    );
                    Calendar now = Calendar.getInstance();
                    dpd.setMaxDate(now);
                    dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                Calendar c = Calendar.getInstance();
                dpd.setMaxDate(c);
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        etDate.setText(date);
    }
    @OnClick(R.id.btn_cek)
    public void submit() {
        if (isValidate()) {
            isLoading(true);
            ApiInterface client = ApiService.createService(ApiInterface.class);
            String nama = etName.getText().toString();
            String date = etDate.getText().toString();
            Call<ZodiakModel> call = client.callZodiak(nama, date);
            call.enqueue(new Callback<ZodiakModel>() {
                @Override
                public void onResponse(Call<ZodiakModel> call, Response<ZodiakModel> response) {

                    if (response.isSuccessful()) {
                        isLoading(false);
                        ArrayList<ZodiakParcel> zodiakModels = new ArrayList<ZodiakParcel>();
                        zodiakModels.add(0, getData(response.body()));
                        Intent i = new Intent(MainActivity.this, DetailActivity.class);
                        i.putParcelableArrayListExtra("zodiak", zodiakModels);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            View sharedView = ivLogo;
                            String transitionName = "logo";
                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                            startActivity(i, activityOptions.toBundle());
                        } else {
                            startActivity(i);
                        }



                    } else {
                        Toast.makeText(MainActivity.this, "Opps, Something wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ZodiakModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public ZodiakParcel getData(ZodiakModel response) {
        ZodiakParcel zodiakParcel = new ZodiakParcel();
        zodiakParcel.setNama(response.getNama());
        zodiakParcel.setLahir(response.getLahir());
        zodiakParcel.setUsia(response.getUsia());
        zodiakParcel.setZodiak(response.getZodiak());
        zodiakParcel.setHarianUmum(response.getRamalan().getHarian().getUmum());
        zodiakParcel.setHarianCintaSingle(response.getRamalan().getHarian().getPercintaan().getSingle());
        zodiakParcel.setHarianCintaCouple(response.getRamalan().getHarian().getPercintaan().getCouple());
        zodiakParcel.setHarianKarir(response.getRamalan().getHarian().getKarirKeuangan());
        zodiakParcel.setMingguanUmum(response.getRamalan().getMingguan().getUmum());

        return zodiakParcel;

    }

    private boolean isValidate() {
        boolean result = true;

        String name = etName.getText().toString();
        String date = etDate.getText().toString();

        if (name == null || name.trim().length() == 0) {
            tillName.setError("Nama tidak boleh kosong");
            result = false;
        } else {
            tillName.setErrorEnabled(false);
        }

        if (date == null || date.trim().length() == 0) {
            tillDate.setError("Tanggal harus diisi");
            result = false;
        } else {
            tillName.setErrorEnabled(false);
        }

        return result;
    }

    private void isLoading(boolean status) {
        if (status) {
            pb_loading.animate().alpha(1.0f).setDuration(500);
            lin_loading.animate().alpha(0.0f).setDuration(500);
        } else {
            pb_loading.animate().alpha(0.0f).setDuration(500);
            lin_loading.animate().alpha(1.0f).setDuration(500);
        }
    }
}

