package com.example.soulfm.payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentAdapterViewHolder>{

    private List<Payment> payments;

    public void setData(List<Payment> payments){
        this.payments = payments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PaymentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapterViewHolder holder, int position) {
        Payment payment = payments.get(position);
        if(payment == null){
            return;
        }
        holder.tv_timePayment.setText(payment.getTimePayment());
        holder.tv_description.setText(payment.getDescription());
        holder.tv_money.setText(payment.getMoney());
    }

    @Override
    public int getItemCount() {
        if(payments != null){
            return payments.size();
        }
        return 0;
    }

    public class PaymentAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_timePayment, tv_description, tv_money;

        public PaymentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_timePayment = itemView.findViewById(R.id.tv_timepayment);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }
}
