package com.corelibs.utils.uploader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 文件上传
 * Created by Ryan on 2016/1/20.
 */
public class ImageUploader {

    private static final String CONTENT_TYPE = "multipart/form-data";
    private static final String CHARSET = "UTF-8";
    private static final String METHOD = "POST";
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static String BOUNDARY;

    public ImageUploader() {
        BOUNDARY = java.util.UUID.randomUUID().toString();
    }

    /**
     * 上传文件, 文件的key值默认为file map的key值. 如fileKey不为空, 则key值统一为fileKey.
     * @param actionUrl 上传的url地址
     * @param params 需传的参数
     * @param files 需传的文件
     * @param fileKey 文件的key
     * @param listener 回调
     */
    public void post(String actionUrl, Map<String, String> params,
                      Map<String, File> files, String fileKey, OnResponseListener listener) {
        URL uri;
        HttpURLConnection conn;
        DataOutputStream outStream;
        try {
            uri = new URL(actionUrl);
            conn = setupConnection(uri);

            outStream = new DataOutputStream(conn.getOutputStream());
            buildOutStream(outStream, params, files, fileKey);

            // 得到响应码
            int res = conn.getResponseCode();

            InputStream in = conn.getInputStream();
            InputStreamReader isReader = new InputStreamReader(in);
            BufferedReader bufReader = new BufferedReader(isReader);
            String line;
            String data = "";

            while ((line = bufReader.readLine()) != null)
                data += line;

            if (res == 200) {
                if (listener != null) listener.onResponse(data);
            } else {
                if (listener != null) listener.onError(new HttpException(res, data));
            }
            outStream.close();
            conn.disconnect();
        } catch (IOException e) {
            if (listener != null) listener.onError(e);
        }
    }

    private HttpURLConnection setupConnection(URL uri) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false);
        conn.setRequestMethod(METHOD); // Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charset", CHARSET);
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
        return conn;
    }

    private StringBuilder buildParams(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append(PREFIX);
                stringBuilder.append(BOUNDARY);
                stringBuilder.append(LINE_END);
                stringBuilder.append("Content-Disposition: form-data; name=\"");
                stringBuilder.append(entry.getKey());
                stringBuilder.append("\"");
                stringBuilder.append(LINE_END);
                stringBuilder.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                stringBuilder.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                stringBuilder.append(LINE_END);
                stringBuilder.append(entry.getValue());
                stringBuilder.append(LINE_END);
            }
        }

        return stringBuilder;
    }

    private StringBuilder buildFile(Map.Entry<String, File> file, String fileKey) {
        StringBuilder stringBuilder = new StringBuilder();

        File tmpFile = file.getValue();
        String param = file.getKey();
        stringBuilder.append(PREFIX);
        stringBuilder.append(BOUNDARY);
        stringBuilder.append(LINE_END);
        stringBuilder.append("Content-Disposition: form-data; name=\"");
        if(fileKey != null && fileKey.length() > 0) stringBuilder.append(fileKey);
        else stringBuilder.append(param);
        stringBuilder.append("\"");
        stringBuilder.append("; filename=\"");
        stringBuilder.append(tmpFile.getName());
        stringBuilder.append("\"");
        stringBuilder.append(LINE_END);
        stringBuilder.append("Content-Type: application/octet-stream; charset=");
        stringBuilder.append(CHARSET);
        stringBuilder.append(LINE_END);
        stringBuilder.append(LINE_END);

        return stringBuilder;
    }

    private void writeFileToStream(Map<String, File> files, String fileKey, DataOutputStream outStream)
            throws IOException {
        if (files != null && files.size() > 0) {
            for (Map.Entry<String, File> file : files.entrySet()) {
                outStream.write(buildFile(file, fileKey).toString().getBytes());
                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINE_END.getBytes());
            }
        }
    }

    private void buildOutStream(DataOutputStream outStream, Map<String, String> params,
                                Map<String, File> files, String fileKey) throws IOException {
        outStream.write(buildParams(params).toString().getBytes());
        writeFileToStream(files, fileKey, outStream);

        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
        outStream.write(end_data);
        outStream.flush();
    }

    public interface OnResponseListener {
        void onResponse(String data);

        void onError(Exception e);
    }

    class HttpException extends Exception {
        private int httpCode;
        private String message;

        public HttpException(int httpCode, String message) {
            this.httpCode = httpCode;
            this.message = message;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public String getMessage() {
            return message;
        }
    }
}
