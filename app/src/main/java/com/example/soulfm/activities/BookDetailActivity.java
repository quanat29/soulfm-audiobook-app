package com.example.soulfm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.api.ApiAddFavoriteBook;
import com.example.soulfm.api.ApiDetailBook;
import com.example.soulfm.api.ApiFavoriteBook;
import com.example.soulfm.api.ApiRemoveFavoriteBook;
import com.example.soulfm.book.Book;
import com.example.soulfm.detail_book.DetailBook;
import com.example.soulfm.favorite_book.FavoriteBook;
import com.example.soulfm.trend_book.TrendBook;
import com.example.soulfm.viewpager_adapter.ViewpagerChapterAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {
    private BottomNavigationView bottom_navigation_book_detail;
    private ViewPager2 view_pager_book_detail;

    private String title;

    private ImageView iv_book_detail, iv_read_more, iv_icon_back_detail, iv_icon_heart_detail;

    private TextView tv_name_book, tv_numRating, tv_book_introduce, tv_book_category, tv_author_name_detail;
    private RatingBar rb_numStar;
    private Boolean isExpanded = false;
    private Boolean isFavorite = false;
    private Button btn_listen_book;
    private int Id_book;
    private int Id_user;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        iv_book_detail = findViewById(R.id.iv_book_detail);
        iv_read_more = findViewById(R.id.iv_read_more);
        tv_name_book = findViewById(R.id.tv_name_book);
        tv_numRating = findViewById(R.id.tv_numRating);
        tv_book_introduce = findViewById(R.id.tv_book_introduce);
        tv_book_category = findViewById(R.id.tv_book_category);
        tv_author_name_detail = findViewById(R.id.tv_author_name_detail);
        rb_numStar = findViewById(R.id.rb_numStar);
        iv_icon_back_detail = findViewById(R.id.iv_icon_back_detail);
        iv_icon_heart_detail = findViewById(R.id.iv_icon_heart_detail);
        btn_listen_book = findViewById(R.id.btn_listen_book);
        sharedPreferences = getSharedPreferences("favorite_color_state", MODE_PRIVATE);

        bottom_navigation_book_detail = findViewById(R.id.bottom_navigation_book_detail);
        view_pager_book_detail = findViewById(R.id.view_pager_book_detail);

        view_pager_book_detail.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        bottom_navigation_book_detail.getMenu().findItem(R.id.menu_chapter).setChecked(true);
                        break;

                    case 1:
                        bottom_navigation_book_detail.getMenu().findItem(R.id.menu_comment).setChecked(true);
                        break;
                }
            }
        });

        bottom_navigation_book_detail.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_chapter:
                        view_pager_book_detail.setCurrentItem(0);
                        break;

                    case R.id.menu_comment:
                        view_pager_book_detail.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

        // nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Id_user = bundle.getInt("Id_user");
        Book book = (Book) bundle.getSerializable("book");
        TrendBook trendBook = (TrendBook) bundle.getSerializable("trend_book");

        if (book != null) {
            // Hiển thị thông tin của Book
            Glide.with(iv_book_detail.getContext())
                    .load(book.getImage_url())
                    .into(iv_book_detail);
            title = book.getTitle();
            tv_name_book.setText(title);
            callApiGetDetailBook(title);
        } else if (trendBook != null) {
            // Hiển thị thông tin của TrendBook
            Glide.with(iv_book_detail.getContext())
                    .load(trendBook.getImage_url())
                    .into(iv_book_detail);
            title = trendBook.getTitle();
            tv_name_book.setText(title);
            callApiGetDetailBook(title);
        } else {
            Toast.makeText(this, "Không có dữ liệu sách hoặc trend book", Toast.LENGTH_SHORT).show();
        }

        // click icon back
        iv_icon_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // click icon favorite
        iv_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readMore();
            }
        });

        // click favorite icon
        iv_icon_heart_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    deleteFavoriteBook(Id_user, Id_book); // Gọi phương thức để xóa favorite book
                } else {
                    addFavoriteBook(Id_user, Id_book); // Gọi phương thức để thêm favorite book
                }
            }
        });

        // bắt sự kiện click btn nghe
        btn_listen_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, BookPlayerActivity.class);
                intent.putExtra("id_book", Id_book);
                intent.putExtra("play_first_episode", true);
                startActivity(intent);
            }
        });

        // lưu trạng thái vào share preference
        restoreFavoriteColorState();
    }

    private void readMore() {
        if (isExpanded) {
            tv_book_introduce.setMaxLines(5);
            iv_read_more.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
        } else {
            tv_book_introduce.setMaxLines(Integer.MAX_VALUE);
            iv_read_more.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
        }
        isExpanded = !isExpanded;
    }

    private void callApiGetDetailBook(String title) {
        ApiDetailBook.apiService.getDetailBook(title).enqueue(new Callback<List<DetailBook>>() {
            @Override
            public void onResponse(Call<List<DetailBook>> call, Response<List<DetailBook>> response) {
                List<DetailBook> detailBooks = response.body();
                if (detailBooks != null && !detailBooks.isEmpty()) {
                    DetailBook bookDetail = detailBooks.get(0); // Chú ý: Lấy phần tử đầu tiên trong danh sách (nếu có)
                    tv_book_introduce.setText(bookDetail.getDescription());
                    tv_numRating.setText(String.valueOf(bookDetail.getNum_comment()) + " đánh giá");
                    rb_numStar.setRating(Float.parseFloat(bookDetail.getNum_star()));
                    tv_author_name_detail.setText(bookDetail.getAuthors());
//                    tv_book_category.setText(bookDetail.getCategory());
                    Id_book = bookDetail.getId_book();
                    ViewpagerChapterAdapter viewpaperChapterAdapter = new ViewpagerChapterAdapter(BookDetailActivity.this, Id_book, Id_user);
                    view_pager_book_detail.setAdapter(viewpaperChapterAdapter);
                    // Xử lý hiển thị tất cả thể loại
                    StringBuilder categoriesBuilder = new StringBuilder();
                    for (DetailBook detail : detailBooks) {
                        if (categoriesBuilder.length() > 0) {
                            categoriesBuilder.append(", "); // Thêm dấu phẩy giữa các thể loại
                        }
                        categoriesBuilder.append(detail.getCategory());
                    }
                    tv_book_category.setText(categoriesBuilder.toString());

                    checkIfFavorite(Id_user, Id_book);
                } else {
                    // Xử lý trường hợp danh sách trả về rỗng hoặc null
                    Toast.makeText(BookDetailActivity.this, "Không có thông tin chi tiết cho sách này", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailBook>> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Đã xảy ra lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfFavorite(int id_user, int id_book) {
        ApiFavoriteBook.apiService.getListFavoriteBook(id_user).enqueue(new Callback<List<FavoriteBook>>() {
            @Override
            public void onResponse(Call<List<FavoriteBook>> call, Response<List<FavoriteBook>> response) {
                if (response.isSuccessful()) {
                    List<FavoriteBook> favoriteBooks = response.body();
                    boolean isBookInFavorites = false;
                    for (FavoriteBook book : favoriteBooks) {
                        if (book.getId_book() == id_book) {
                            isBookInFavorites = true;
                            break;
                        }
                    }
                    // Cập nhật màu cho biểu tượng yêu thích
                    if (isBookInFavorites) {
                        iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.red));
                        isFavorite = true;
                    } else {
                        iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.white));
                        isFavorite = false;
                    }
                    // Lưu trạng thái vào shared preferences
                    saveFavoriteColorState(isFavorite);
                } else {
                    // Xử lý khi không thể lấy danh sách yêu thích từ máy chủ
                    Toast.makeText(BookDetailActivity.this, "Không thể lấy danh sách yêu thích từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FavoriteBook>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Toast.makeText(BookDetailActivity.this, "Đã xảy ra lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFavoriteBook(int id_user, int id_book) {
        ApiAddFavoriteBook.apiService.addFavoriteBook(id_book, id_user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    saveFavoriteColorState(true);
                    iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.red));
                    Toast.makeText(BookDetailActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookDetailActivity.this, "Không thể thêm sách vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Đã xảy ra lỗi khi thêm sách vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteFavoriteBook(int id_user, int id_book) {
        ApiRemoveFavoriteBook.apiService.RemoveFavoriteBook(id_book, id_user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa thành công, cập nhật trạng thái yêu thích và màu cho ImageView
                    saveFavoriteColorState(false); // Cập nhật trạng thái yêu thích trong SharedPreferences
                    iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.white)); // Cập nhật màu cho ImageView
                    Toast.makeText(BookDetailActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    // Xóa không thành công, hiển thị thông báo lỗi
                    Toast.makeText(BookDetailActivity.this, "Không thể xóa favorite book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Đã xảy ra lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFavoriteColorState(boolean isFavorite) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_favorite", isFavorite);
        editor.apply();
        this.isFavorite = isFavorite;
    }

    // Phương thức để khôi phục trạng thái màu yêu thích của ImageView
    private void restoreFavoriteColorState() {
        boolean isFavorite = sharedPreferences.getBoolean("is_favorite_" + Id_book, false);
        if (isFavorite) {
            iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.red));
        } else {
            iv_icon_heart_detail.setColorFilter(getResources().getColor(R.color.white));
        }
        this.isFavorite = isFavorite;
    }
}
