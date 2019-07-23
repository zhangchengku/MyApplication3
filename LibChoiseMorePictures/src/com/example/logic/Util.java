package com.example.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.logic.ImageUtils.ImageSize;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

public class Util {

    Context context;

    public Util(Context context) {
        this.context = context;
    }

    /**
     * 获取全部图片地址
     *
     * @return
     */
    public ArrayList<String> listAlldir() {
        ArrayList<String> list = new ArrayList<>();
        if (context == null) return list;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Cursor cursor = context.getContentResolver().query(intent.getData(),
                new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor == null) return list;
        ArrayList<File> listFile = new ArrayList<>();
        File file;
        String path;
        while (cursor.moveToNext()) {
            try {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            } catch (IllegalArgumentException e) {
                path = null;
            }
            if (path != null && path.trim().length() > 0) {
                file = new File(path);
                if (file.exists()) {
                    listFile.add(file);
                }
            }
        }
        cursor.close();
        if (listFile.size() > 0) {
            Collections.sort(listFile, new FileComparator());// 按照时间倒序排序
            for (int i = 0; i < listFile.size(); i++) {
                list.add(listFile.get(i).getAbsolutePath());
            }
        }
        return list;
    }

    public List<FileTraversal> LocalImgFileList() {
        List<FileTraversal> data = new ArrayList<FileTraversal>();
        String filename = "";
        List<String> allimglist = listAlldir();
        List<String> retulist = new ArrayList<String>();

        if (allimglist != null) {
            ArrayList<String> listFileName = new ArrayList<String>();
            for (int i = 0; i < allimglist.size(); i++) {
                retulist.add(getfileinfo(allimglist.get(i)));
            }
            for (int i = 0; i < retulist.size(); i++) {
                if (!isExitListFileName(listFileName, retulist.get(i))) {
                    listFileName.add(retulist.get(i));
                }
            }

            for (int i = 0; i < listFileName.size(); i++) {
                filename = listFileName.get(i);
                FileTraversal ftl = new FileTraversal();
                ftl.filename = filename;
                data.add(ftl);
            }

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < allimglist.size(); j++) {
                    if (data.get(i).filename.equals(getfileinfo(allimglist.get(j)))) {
                        data.get(i).filecontent.add(allimglist.get(j));
                    }
                }
            }
        }
        return data;
    }

    public boolean isExitListFileName(ArrayList<String> listFileName, String str) {
        boolean bol = false;
        for (int i = 0; i < listFileName.size(); i++) {
            if (listFileName.get(i).equals(str)) {
                bol = true;
                break;
            }
        }
        return bol;
    }

    /**
     * 按照时间倒序排序
     */
    private class FileComparator implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            // 按文件最近修改的时间排序
//			long lmodified = lhs.lastModified();
//			long rmodified = rhs.lastModified();
//			return (lmodified < rmodified) ? -1 : ((lmodified == rmodified) ? 0 : 1);
            if (lhs.lastModified() < rhs.lastModified()) {
                return 1;// 最后修改的照片在前
            } else if (lhs.lastModified() == rhs.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    // 显示原生图片尺寸大小
    public Bitmap getPathBitmap(Uri imageFilePath, int dw, int dh) throws FileNotFoundException {
        // 获取屏幕的宽和高
        /**
         * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
         * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
         * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
         * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
         */
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        // 由于使用了MediaStore存储，这里根据URI获取输入流的形式
        Bitmap pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath), null, op);

        int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
        int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例

        /**
         * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
         * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
         * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
         */
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
        pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath), null, op);

        return pic;
    }

    public String getfileinfo(String data) {
        String filename[] = data.split("/");
        if (filename != null) {
            return filename[filename.length - 2];
        }
        return null;
    }

    public void imgExcute(ImageView imageView, ImgCallBack icb, String... params) {
        LoadBitAsynk loadBitAsynk = new LoadBitAsynk(imageView, icb);
        loadBitAsynk.execute(params);
    }

    public class LoadBitAsynk extends AsyncTask<String, Integer, Bitmap> {

        ImageView imageView;
        ImgCallBack icb;

        LoadBitAsynk(ImageView imageView, ImgCallBack icb) {
            this.imageView = imageView;
            this.icb = icb;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        ImageSize size = ImageUtils.getImageViewSize(imageView);
                        bitmap = ImageUtils.decodeSampledBitmapFromResource(params[i], size.width, size.height);
                        // bitmap = getPathBitmap(
                        // Uri.fromFile(new File(params[i])), 200, 200);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                // imageView.setImageBitmap(result);
                icb.resultImgCall(imageView, result);
            }
        }

    }

    /**
     * 更改字体
     *
     * @param context 上下文
     * @param uri     资源路径
     * @return 字体对象
     */
    public static Typeface fontType(Context context, String uri) {
        return Typeface.createFromAsset(context.getAssets(), uri);
    }

}
