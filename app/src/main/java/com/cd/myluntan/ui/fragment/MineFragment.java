package com.cd.myluntan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.ui.activity.EditProfileActivity;
import com.cd.myluntan.ui.activity.LoginActivity;
import com.cd.myluntan.ui.activity.MainActivity;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYID;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class MineFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout attentionLL, fanLL, accessLL, rankLL, dynamic, topic, history, set;
    private TextView personal, signIn, attentionNum, attention, fanNum, fan, accessNum, access, rankNum, rank, name, logOut;
    private ImageView QR_code, avatar;

    User myUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        myUser = Singletion.getInstance().getUser();
        initView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String, String> map = new HashMap<>();
        map.put("id", myUser.getId());
        Data_Access.AccessStringDate(URL + GETUSERBYID, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                TypeToken<User> userTypeToken = new TypeToken<User>() {
                };
                Gson gson = new Gson();
                User user2 = null;
                try {
                    user2 = gson.fromJson(response.body().string(), userTypeToken.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Singletion.getInstance().setUser(user2);
                myUser = user2;
                Log.e("erer", "wererr");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initShow();
                    }
                });
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.e("erer", "werer22r");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                Log.e("erer", "werer33r");

            }
        });
    }

    private void initShow() {
        name.setText(myUser.getNick() != null ? myUser.getNick() : myUser.getName());
        if (myUser.getHeadurl().length() < 24) {
            Glide.with(getContext()).load(URL + SHOWIMGS + "?name=" + myUser.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatar);
        } else {
            Glide.with(getContext()).load(myUser.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatar);
        }
    }

    private void initView() {
        personal = view.findViewById(R.id.personal);
        attentionLL = view.findViewById(R.id.attentionLL);
        fanLL = view.findViewById(R.id.fanLL);
        accessLL = view.findViewById(R.id.accessLL);
        rankLL = view.findViewById(R.id.rankLL);
        dynamic = view.findViewById(R.id.dynamic);
        name = view.findViewById(R.id.username);
        topic = view.findViewById(R.id.topic);
        history = view.findViewById(R.id.history);
        set = view.findViewById(R.id.set);
        signIn = view.findViewById(R.id.signIn);
        attentionNum = view.findViewById(R.id.attentionNum);
        attention = view.findViewById(R.id.attention);
        fanNum = view.findViewById(R.id.fanNum);
        fan = view.findViewById(R.id.fan);
        accessNum = view.findViewById(R.id.accessNum);
        access = view.findViewById(R.id.access);
        rankNum = view.findViewById(R.id.rankNum);
        rank = view.findViewById(R.id.rank);
        QR_code = view.findViewById(R.id.QR_code);
        avatar = view.findViewById(R.id.avatar);
        logOut = view.findViewById(R.id.logOut);
        attentionLL.setOnClickListener(this);
        fanLL.setOnClickListener(this);
        accessLL.setOnClickListener(this);
        rankLL.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        topic.setOnClickListener(this);
        history.setOnClickListener(this);
        set.setOnClickListener(this);
        personal.setOnClickListener(this);
        signIn.setOnClickListener(this);
        QR_code.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attentionLL:
                Toast.makeText(view.getContext(), "attentionLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fanLL:
                Toast.makeText(view.getContext(), "fanLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.accessLL:
                Toast.makeText(view.getContext(), "accessLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rankLL:
                Toast.makeText(view.getContext(), "rankLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dynamic:
                Toast.makeText(view.getContext(), "dynamic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.topic:
                Toast.makeText(view.getContext(), "topic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:
                Toast.makeText(view.getContext(), "history", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(view.getContext(), "set", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signIn:
                Toast.makeText(view.getContext(), "signIn", Toast.LENGTH_SHORT).show();
                break;
            case R.id.QR_code:
                Toast.makeText(view.getContext(), "QR_code", Toast.LENGTH_SHORT).show();
                break;
            case R.id.personal:
                Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logOut:
                logOut.setText("退出中...");
                logOut.setEnabled(false);
                EMClient.getInstance().logout(true, new EMCallBackAdapter() {

                    @Override
                    public void onSuccess() {
                        logout("退出成功");
                        SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name","");
                        editor.putString("pass","");
                        editor.commit();
                        view.getContext().startActivity(new Intent(view.getContext(),LoginActivity.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }

                    @Override
                    public void onError(int code, String message) {
                        logout(message);
                    }
                });
                break;
        }
    }

    private void logout(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logOut.setEnabled(true);
                logOut.setText(message);
            }
        });
    }
}