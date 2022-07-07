package in.datapro.pattern1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FileManagerActivity extends AppCompatActivity {


    ImageView imageView;
    Button button;
    StorageReference firebaseStorage;
    ProgressDialog progressDialog;
    final static int REQUESTCODE = 4;

    private GridView gridView;
    private GridViewAdapter gridAdapter;

     ArrayList<ImageItem> imageItems2 = new ArrayList<>();

    String username = "vijay";

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);


        gridView = (GridView) findViewById(R.id.gvfiles);
        gridAdapter = new GridViewAdapter(
                this, R.layout.grid_item_layout, imageItems2);
        gridView.setAdapter(gridAdapter);

//        int k=0; if(k<1) return;

        Intent intent = getIntent();
        //username = "vijay";
        username = intent.getStringExtra("username");

        imageView = findViewById(R.id.imageView);


        button = findViewById(R.id.button);

        firebaseStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(FileManagerActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        showImages();

//        // Reference to an image file in Cloud Storage
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(username+"/image");
////        Glide.with(this)
////                .load("https://firebasestorage.googleapis.com/v0/b/pattern1-3bd6d.appspot.com/o/charan%2Fimage%2Fstorage%2Femulated%2F0%2FSnapchat%2FSnapchat-2110954730.jpg.png?alt=media&token=9528d2b8-9729-49da-8d26-c2430d4340ae")
////                .into(imageView);
//
//
//        storageReference.listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        for (StorageReference prefix : listResult.getPrefixes()) {
//                            // All the prefixes under listRef.
//                            // You may call listAll() recursively on them.
//
//                        }
//
//                        for (StorageReference item : listResult.getItems()) {
//                            // All the items under listRef.
//                            System.out.println("firebase files :" + item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    String imageURL = uri.toString();
//                                    imageItems2.add(new ImageItem(imageURL));
//                                    //gridView.invalidateViews();
//                                    gridAdapter.notifyDataSetChanged();
//                                    gridView.setAdapter(gridAdapter);
//                                    //Glide.with(getApplicationContext()).load(imageURL).into(imageView);
//                                    System.out.println("firebase files download url " + imageURL);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle any errors
//                                }
//                            }));
////                            Glide.with(FileManagerActivity.this)
////                                    .load(item.getDownloadUrl())
////                                    .into(imageView);
//
//                        }
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Uh-oh, an error occurred!
//                    }
//                });
//
//
////        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////            @Override
////            public void onSuccess(Uri uri) {
////                String imageURL = uri.toString();
////                Glide.with(getApplicationContext()).load(imageURL).into(imageView);
////                    System.out.println("firebase files download url "+imageURL);
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception exception) {
////                // Handle any errors
////            }
////        });


    }


    public void showImages()
    {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(username+"/image");
//        Glide.with(this)
//                .load("https://firebasestorage.googleapis.com/v0/b/pattern1-3bd6d.appspot.com/o/charan%2Fimage%2Fstorage%2Femulated%2F0%2FSnapchat%2FSnapchat-2110954730.jpg.png?alt=media&token=9528d2b8-9729-49da-8d26-c2430d4340ae")
//                .into(imageView);


        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.

                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            System.out.println("firebase files :" + item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageURL = uri.toString();
                                    imageItems2.add(new ImageItem(imageURL));
                                    //gridView.invalidateViews();
                                    gridAdapter.notifyDataSetChanged();
                                    gridView.setAdapter(gridAdapter);
                                    //Glide.with(getApplicationContext()).load(imageURL).into(imageView);
                                    System.out.println("firebase files download url " + imageURL);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            }));
//                            Glide.with(FileManagerActivity.this)
//                                    .load(item.getDownloadUrl())
//                                    .into(imageView);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });


//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String imageURL = uri.toString();
//                Glide.with(getApplicationContext()).load(imageURL).into(imageView);
//                    System.out.println("firebase files download url "+imageURL);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

    }

    // For a simple image list:
//    @Override public View getView(int position, View recycled, ViewGroup container) {
//        final ImageView myImageView;
//        if (recycled == null) {
//            myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
//        } else {
//            myImageView = (ImageView) recycled;
//        }
//
//        String url = myUrls.get(position);
//
//        Glide
//                .with(myFragment)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
//                .into(myImageView);
//
//        return myImageView;
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE) {
            progressDialog.setMessage("Uploading...");
            Uri uri = data.getData();
            progressDialog.show();
            imageView.setImageURI(uri);

            System.out.println("test data "+uri);

            //StorageReference fileName = firebaseStorage.child(username + "/image" + uri.getLastPathSegment() + ".png");
            String[] parts=uri.getLastPathSegment().split("/");
            for(String p:parts) {
                System.out.println("test data "+p);
            }
            StorageReference fileName = firebaseStorage.child(
                    username + "/image/" + parts[parts.length-1] );
            fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FileManagerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    showImages();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FileManagerActivity.this, "Uploaded?", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}