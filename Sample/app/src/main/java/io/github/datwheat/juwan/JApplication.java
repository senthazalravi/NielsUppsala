package io.github.datwheat.juwan;

import android.app.Application;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper;
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory;
import com.squareup.leakcanary.LeakCanary;

import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class JApplication extends Application {
    private static final String BASE_URL = "https://api.graph.cool/simple/v1/cj2brg6o56vkn01044qv2wwab";
    private static final String SQL_CACHE_NAME = "juwan";

    private ApolloClient apolloClient;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        ApolloSqlHelper apolloSqlHelper = ApolloSqlHelper.create(this, SQL_CACHE_NAME);


        NormalizedCacheFactory cacheFactory = new SqlNormalizedCacheFactory(apolloSqlHelper);

        CacheKeyResolver<Map<String, Object>> resolver = new CacheKeyResolver<Map<String, Object>>() {
            @Nonnull
            @Override
            public CacheKey resolve(@Nonnull Map<String, Object> objectSource) {
                String id = (String) objectSource.get("id");
                if (id == null || id.isEmpty()) {
                    return CacheKey.NO_KEY;
                }
                return CacheKey.from(id);
            }
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        apolloClient = ApolloClient.<ApolloCall>builder()
                .serverUrl(BASE_URL)
                .normalizedCache(cacheFactory, resolver)
                .okHttpClient(okHttpClient)
                .build();
    }

    public ApolloClient apolloClient() {
        return apolloClient;
    }
}