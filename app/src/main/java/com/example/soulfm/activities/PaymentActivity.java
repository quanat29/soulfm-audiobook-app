package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.soulfm.R;
import com.example.soulfm.payment.Payment;
import com.example.soulfm.payment.PaymentAdapter;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView rcv_list_payment;
    private PaymentAdapter paymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        rcv_list_payment = findViewById(R.id.rcv_list_payment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_list_payment.setLayoutManager(linearLayoutManager);
        paymentAdapter = new PaymentAdapter();
        paymentAdapter.setData(getPaymentList());
        rcv_list_payment.setAdapter(paymentAdapter);
    }

    private List<Payment> getPaymentList() {
        List<Payment> payments = new ArrayList<>();
         payments.add(new Payment("SOULFM 12 THÁNG", "365 ngày nghe sách miễn phí", "329.000VNĐ"));
        payments.add(new Payment("SOULFM 6 THÁNG", "180 ngày nghe sách miễn phí", "179.000VNĐ"));
        payments.add(new Payment("SOULFM 3 THÁNG", "90 ngày nghe sách miễn phí", "99.000VNĐ"));

        return payments;
    }
}