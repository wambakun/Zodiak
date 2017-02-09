package developerkampus.zodiak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wambakun on 08/02/2017.
 */

public class MingguanModel {
    @SerializedName("umum")
    @Expose
    private String umum;
    @SerializedName("percintaan")
    @Expose
    private PercintaanModel percintaan ;
    @SerializedName("karir_keuangan")
    @Expose
    private String karirKeuangan;

    public String getUmum() {
        return umum;
    }

    public void setUmum(String umum) {
        this.umum = umum;
    }

    public PercintaanModel getPercintaan() {
        return percintaan;
    }

    public void setPercintaan(PercintaanModel percintaan) {
        this.percintaan = percintaan;
    }

    public String getKarirKeuangan() {
        return karirKeuangan;
    }

    public void setKarirKeuangan(String karirKeuangan) {
        this.karirKeuangan = karirKeuangan;
    }
}
