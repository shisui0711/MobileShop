package com.example.fruitshop.Infrastructure.Data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fruitshop.Domain.Entities.Category;
import com.example.fruitshop.Domain.Entities.DetailOrder;
import com.example.fruitshop.Domain.Entities.Favorite;
import com.example.fruitshop.Domain.Entities.Order;
import com.example.fruitshop.Domain.Entities.Product;
import com.example.fruitshop.Domain.Entities.User;
import com.example.fruitshop.Infrastructure.Data.DAO.CategoryDao;
import com.example.fruitshop.Infrastructure.Data.DAO.ProductDao;
import com.example.fruitshop.Infrastructure.Data.DAO.UserDao;

import java.util.ArrayList;
import java.util.concurrent.Executors;

@Database(
        entities = {User.class, Category.class, Product.class, Order.class, Favorite.class, DetailOrder.class},
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app.db")
                            .addCallback(seedDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback seedDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Seed dữ liệu mặc định
            new Thread(() -> {
                AppDatabase database = INSTANCE;
                if (database != null) {
                    ArrayList<Category> categories = new ArrayList<>();
                    categories.add(new Category(1,"Rau củ","cat1"));
                    categories.add(new Category(2,"Hoa quả","cat2"));
                    categories.add(new Category(3,"Sữa","cat3"));
                    categories.add(new Category(4,"Đồ uống","cat4"));
                    categories.add(new Category(5,"Đồ uống","cat5"));
                    database.categoryDao().addRange(categories);

                    ArrayList<Product> products = new ArrayList<>();
                    products.add(new Product(1,"Cam",2,10000,10,"orange"));
                    products.add(new Product(2,"Dứa",2,10000,10,"pineapple"));
                    products.add(new Product(3,"Dưa hấu",2,10000,10,"watermelon"));
                    products.add(new Product(4,"Dâu tây",2,10000,10,"strawberry"));
                    database.productDao().addRange(products);
                }
            }).start();
        }
    };

}
