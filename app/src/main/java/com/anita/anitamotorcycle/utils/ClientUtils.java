package com.anita.anitamotorcycle.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.beans.UserBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Anita
 * @description:请求服务器数据
 * @date : 2020/2/4 12:35
 */
public class ClientUtils {

    private static final String TAG = "ClientUtils";
    private static int sLine;
    private static MotorBean sMotorBean;
    private static UserBean sUserBean;
    private static boolean sResult;
    private static List<MotorBean> sMotorList = null;
    private static List<RecordBean> sRecordingList = null;
    private static Bitmap sBitmap = null;
    private static RecordBean sRecordBean;

    //                String url = "http://192.168.0.107:8080/AMServer/userlogin";
//                Map<String, String> params = new HashMap<>();
//                ClientUtils.getUserData(url, params);
    public static void getUserData(String baseUrl, Map<String, String> params) {
        try {
            //拼接URL
            StringBuilder sb = new StringBuilder(baseUrl);
            if (params.size() > 0) {
                sb.append("?");
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                }
            }
            String resultUrl = sb.toString();
            URL url = new URL(resultUrl);
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //设置请求超时时间
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "keep-alive");
            urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh");

            //开始连接
            urlConnection.connect();
            //获取返回内容
            System.out.println("获取返回内容");
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
            for (Map.Entry<String, List<String>> entry : entries) {
                System.out.println(entry.getKey() + " === " + entry.getValue());
            }
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line---" + line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据currentMotorId获取摩托车信息
     *
     * @param phone
     * @return
     */
    public static UserBean getUserInfo(final String phone) {
        Log.d(TAG, "getUserInfo/AMServer/getuser: 获取用户信息");
        sUserBean = null;
        Thread getUserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream;
                InputStream inputStream;
                try {
                    URL url = new URL(Constants.BASEURL + "/AMServer/getuser");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

                    UserBean userBean = new UserBean(phone);
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(userBean);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "jsonStr --- " + jsonStr);
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给到服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String line = in.readLine();
                        Log.d(TAG, "server response data --- " + line);
                        if (line != null) {
                            sUserBean = JsonUtils.userFromJson(line);
                        }
                        in.close();
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (SocketTimeoutException e) {
                    Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getUserThread.start();
        try {
//            主线程等待子线程结束
            getUserThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getUserThread done!");

        return sUserBean;
    }


    /**
     * 根据currentMotorId获取摩托车信息
     *
     * @param context
     * @param currentMotorId
     * @return
     */
    public static MotorBean getCurrentMotor(Context context, final String currentMotorId) {
        Log.d(TAG, "getCurrentMotor/AMServer/getmotor: 获取当前摩托车信息");
        sMotorBean = null;

        Thread getCurrentMotorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream;
                InputStream inputStream;
                try {
                    URL url = new URL(Constants.BASEURL + "/AMServer/getmotor");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

                    MotorBean motorBean = new MotorBean();
                    motorBean.setId(currentMotorId);
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(motorBean);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "jsonStr --- " + jsonStr);
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给到服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String line = in.readLine();
                        Log.d(TAG, "server response data --- " + line);
                        if (line != null) {
                            sMotorBean = JsonUtils.motorFromJson(line);
                        }
                        in.close();
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (SocketTimeoutException e) {
                    Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getCurrentMotorThread.start();
        try {
//            主线程等待子线程结束
            getCurrentMotorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getCurrentMotorThread done!");

        return sMotorBean;
    }


    /**
     * 根据userphone获取当前用户所有摩托车信息
     *
     * @param context
     * @param phone
     * @return
     */
    public static List<MotorBean> getMotorList(final Context context, final String phone) {
        Log.d(TAG, "getMotorList: 获取所有摩托车信息");
        sMotorList = null;
        Thread getMotorsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream;
                InputStream inputStream;
                try {
                    URL url = new URL(Constants.BASEURL + "/AMServer/getmotors");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

                    UserBean user = new UserBean(phone, null);
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(user);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "jsonStr --- " + jsonStr);
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给到服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String line = in.readLine();
                        Log.d(TAG, "server response data --- " + line);
                        if (line != null) {
                            sMotorList = JsonUtils.motorListFromJson(line);

                        }
                        in.close();
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (SocketTimeoutException e) {
                    Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        getMotorsThread.start();
        try {
//            主线程等待子线程结束
            getMotorsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getMotorListThread done!");
        return sMotorList;
    }

    /**
     * 根据userphone获取当前用户维修中/维修完成记录
     * @param phone
     * @return
     */
    public static List<RecordBean> getRepairingList(final String phone, final int mark) {
        Log.d(TAG, "getRepairingList: 获取维修记录列表");
        sRecordingList = null;
        Thread getRepairingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream;
                InputStream inputStream;
                try {
                    URL url = new URL(Constants.BASEURL + "/AMServer/GetRecords");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

                    UserBean user = new UserBean(phone, mark);
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(user);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "jsonStr --- " + jsonStr);
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给到服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String line = in.readLine();
                        Log.d(TAG, "server response data --- " + line);
                        if (line != null) {
                            sRecordingList = JsonUtils.recordListFromJson(line);

                        }
                        in.close();
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (SocketTimeoutException e) {
                    Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        getRepairingThread.start();
        try {
//            主线程等待子线程结束
            getRepairingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getRepairingThread done!");
        return sRecordingList;
    }

    /**
     * 登录验证
     * 1. 连接服务端数据库，传递数据
     * 2. 验证用户手机号已注册
     * 3. 验证密码正确
     *
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateLoginPost(Context context, String phone, String password) {
        Log.d(TAG, "validateLoginPost: 登录验证");
        boolean result = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/userlogin");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            UserBean userBean = new UserBean(phone, password);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(userBean);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "jsonStr --- " + jsonStr);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            //连接
            httpURLConnection.connect();
            //把数据给到服务
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            //拿结果
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line = in.readLine();
                Log.d(TAG, "server response data --- " + line);
                if (line != null) {
                    sLine = Integer.parseInt(line);
                    if (sLine < 0) {
                        Looper.prepare();
                        Toast.makeText(context, "该手机号未注册", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    } else if (sLine == 0) {
                        Looper.prepare();
                        Toast.makeText(context, "密码输入错误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else if (sLine > 0) {
                        result = true;
                    }
                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验证vin码
     * 验证是否存在该vin码，且该车辆未曾添加
     * （若存在，返回摩托车数据，更新ui）
     *
     * @param vin
     * @return
     */
    public static MotorBean validateVIN(final String vin, final Context context) {
        Log.d(TAG, "validateVIN: 验证车架号");
        sMotorBean = null;
//        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/vinvalidate");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            MotorBean motorBean = new MotorBean(vin);
            Log.d(TAG, "post数据---" + motorBean.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorBean);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            //连接
            httpURLConnection.connect();
            //把数据给到服务
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            //拿结果
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line = in.readLine();
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sMotorBean = JsonUtils.motorFromJson(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }

        return sMotorBean;
    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. 验证车牌号是否当前数据库
     * （若在，返回true;若不在，返回false）
     *
     * @param motorBean
     * @return
     */
    public static boolean validateNumbers(Context context, MotorBean motorBean) {
        Log.d(TAG, "validateNumbers: 验证车牌号");
        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/pnumsvalidate");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            Log.d(TAG, "post数据---" + motorBean.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorBean);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            //连接
            httpURLConnection.connect();
            //把数据给到服务
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            //拿结果
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line = in.readLine();
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sResult = Boolean.parseBoolean(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }
        return sResult;
    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. update数据库摩托车信息
     * （更新成功返回true，失败false）
     *
     * @param motorBean
     * @return
     */
    public static boolean addMotor(Context context, MotorBean motorBean) {
        Log.d(TAG, "addMotor: 添加摩托车");
        sResult = false;
        OutputStream outputStream;
        InputStream inputStream;
        try {
            URL url = new URL(Constants.BASEURL + "/AMServer/updatemotor");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

            Log.d(TAG, "post数据---" + motorBean.toString());
            Gson gson = new Gson();
            String jsonStr = gson.toJson(motorBean);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            Log.d(TAG, "生成json--- " + jsonStr);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            //连接
            httpURLConnection.connect();
            //把数据给到服务
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            //拿结果
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line = in.readLine();
                Log.d(TAG, "服务器响应数据 --- " + line);
                if (line != null) {
                    sResult = Boolean.parseBoolean(line);

                }
                in.close();
                inputStream.close();
            }
            outputStream.close();
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
            Looper.prepare();
            Toast.makeText(context, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
        }
        return sResult;
    }

    /**
     * 申请维修（添加维修记录）
     *
     * @param recordBean 传回申请信息、用户phone、和申请时间
     * @return 返回是否添加成功。成功：record，失败：无数据，连接服务器失败：null
     */
    public static RecordBean addRecord(final RecordBean recordBean) {
        Log.d(TAG, "addRecord: 添加维修记录");
        sRecordBean = null;
        Thread addRecordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream;
                InputStream inputStream;
                try {
                    URL url = new URL(Constants.BASEURL + "/AMServer/addrecord");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");

                    Log.d(TAG, "post数据---" + recordBean.toString());
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(recordBean);
                    byte[] bytes = jsonStr.getBytes("UTF-8");
                    Log.d(TAG, "生成json--- " + jsonStr);
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    //连接
                    httpURLConnection.connect();
                    //把数据给到服务
                    outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String line = in.readLine();
                        Log.d(TAG, "服务器响应数据 --- " + line);
                        if (line != null) {
                            if (line == "flase") {
                                sRecordBean = new RecordBean();
                            } else {
                                sRecordBean = JsonUtils.recordFromJson(line);;
                            }
                        }
                        in.close();
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (SocketTimeoutException e) {
                    Log.d(TAG, "SocketTimeoutException: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "validateVIN:Exception-- " + e.getMessage());
                }
            }
        });
        addRecordThread.start();
        try {
//            主线程等待子线程结束
            addRecordThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("addRecordThread done!");
        return sRecordBean;
    }


    /**
     * 访问网络图片
     */
    public static Bitmap loadImage(String urlStr) {
        Log.d(TAG, "loadImage: 访问网络图片");
        Bitmap bitmap = null;
        try {
            // 定义一个URL对象
            URL url = new URL(urlStr);
            // 打开该URL对应的资源输入流
            InputStream is = url.openStream();
            // 从InputStream中解析出图片
            bitmap = BitmapFactory.decodeStream(is);
            // 发送消息，通知UI组件加载图片

            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "loadImage: MalformedURLException--" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 访问网络图片
     */
    public static Bitmap loadImage1(final String urlStr) {
        Log.d(TAG, "loadImage1: 访问网络图片");
        sBitmap = null;
        Thread getImageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    HttpURLConnection conn = null;
                    url = new URL(urlStr);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        sBitmap = BitmapFactory.decodeStream(inputStream);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getImageThread.start();
        try {
//            主线程等待子线程结束
            getImageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sBitmap;

    }


}
