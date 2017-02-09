package developerkampus.zodiak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wambakun on 08/02/2017.
 */

public class RamalanModel {
    @SerializedName("harian")
    @Expose
    private HarianModel harian;
    @SerializedName("mingguan")
    @Expose
    private MingguanModel mingguan;

    public HarianModel getHarian() {
        return harian;
    }

    public void setHarian(HarianModel harian) {
        this.harian = harian;
    }

    public MingguanModel getMingguan() {
        return mingguan;
    }

    public void setMingguan(MingguanModel mingguan) {
        this.mingguan = mingguan;
    }
}
