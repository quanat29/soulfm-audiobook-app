package com.example.soulfm.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookDetailActivity;
import com.example.soulfm.api.ApiAddComment;
import com.example.soulfm.api.ApiComment;
import com.example.soulfm.comment.Comment;
import com.example.soulfm.comment.CommentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private static final String ARG_BOOK_ID = "Id_book";
    private static final String ARG_USER_ID = "Id_user";

    private RecyclerView rcv_listComment;
    private View view;
    private CommentAdapter commentAdapter;

    private int bookId, userId;

    private TextView tv_sum_rating, tv_number_rating;
    private LinearLayout layout_insert_comment;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(int bookId, int userId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookId = getArguments().getInt(ARG_BOOK_ID);
            userId = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_comment, container, false);
        initUI();
        callApiComment(bookId);
        writeComment();
        return view;
    }

    private void writeComment() {
        layout_insert_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_write_comment, null);

                //create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                EditText etCommentContent = dialogView.findViewById(R.id.et_comment_content);
                RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
                Button btnSubmitComment = dialogView.findViewById(R.id.btn_submit_comment);

                btnSubmitComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String commentContent = etCommentContent.getText().toString();
                        int rating = (int) ratingBar.getRating();

                        if(commentContent.isEmpty()){
                            Toast.makeText(getContext(),"Vui lòng nhập nội dung đánh giá", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        saveCommentToDB(commentContent, rating);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void saveCommentToDB(String commentContent, int rating) {
        ApiAddComment.apiService.addFavoriteBook(bookId, userId, commentContent, rating).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm comment thành công", Toast.LENGTH_SHORT).show();
                    callApiComment(bookId);
                } else {
                    Toast.makeText(getContext(), "Không thể thêm comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initUI() {
        rcv_listComment = view.findViewById(R.id.rcv_list_comment);
        commentAdapter = new CommentAdapter();
        tv_number_rating = view.findViewById(R.id.tv_number_rating);
        tv_sum_rating = view.findViewById(R.id.tv_sum_rating);
        layout_insert_comment = view.findViewById(R.id.layout_insert_comment);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rcv_listComment.setLayoutManager(linearLayoutManager);
        rcv_listComment.setAdapter(commentAdapter);
    }

    private void callApiComment(int bookId) {
        ApiComment.apiService.getlistComment(bookId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> comments = response.body();
                    if (!comments.isEmpty()) {
                        Comment comment = comments.get(0);
                        tv_sum_rating.setText(comment.getNum_star());
                        tv_number_rating.setText(String.valueOf(comment.getNum_comment())+ " đánh giá");
                        commentAdapter.setData(comments);

                    } else {
                        tv_sum_rating.setText("0.0");
                        tv_number_rating.setText("0 đánh giá");
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
                Log.e("ChapterFragment", "onFailure: ", t);
            }
        });
    }
}