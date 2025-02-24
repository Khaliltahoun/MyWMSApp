package org.example.mywmsapp.model;

import java.util.Date;

public class BarcodeScan {
    private int id;
    private String barcode;
    private Date scanDate;
    private int warehouseId;

    public BarcodeScan() {}

    public BarcodeScan(int id, String barcode, Date scanDate, int warehouseId) {
        this.id = id;
        this.barcode = barcode;
        this.scanDate = scanDate;
        this.warehouseId = warehouseId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public Date getScanDate() { return scanDate; }
    public void setScanDate(Date scanDate) { this.scanDate = scanDate; }

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }

    @Override
    public String toString() {
        return "BarcodeScan{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", scanDate=" + scanDate +
                ", warehouseId=" + warehouseId +
                '}';
    }
}
