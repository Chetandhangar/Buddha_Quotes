package com.example.buddhaquotes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddhaquotes.Quote;
import com.example.buddhaquotes.R;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public TextView quoteTextView;
    public ImageView nextButtonImageVIew;
    public ImageView prevButtonImageVIew;
    public ImageView shareButtonImageView;
    public ImageView copyButtonImageView;
    public ArrayList<Quote> quoteList;
    public Stack<Quote> previousQuotes;
    public ImageView bagchangeImagview;
    public int index;
    public boolean isPrevious;
    public ImageView bagImageView;
    private  int current_image;
    int[] images={R.drawable.buddha_bg,R.drawable.buddha_bg1,R.drawable.buddha_bg2,R.drawable.buddha_bg2,R.drawable.buddha_bg3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quoteTextView = (TextView) findViewById(R.id.textView);
        nextButtonImageVIew = (ImageView) findViewById(R.id.next_button);
        prevButtonImageVIew = (ImageView) findViewById(R.id.prev_button);
        shareButtonImageView = (ImageView) findViewById(R.id.share_button);
        copyButtonImageView = (ImageView) findViewById(R.id.copy_button);
        bagImageView = (ImageView) findViewById(R.id.back_image);
        bagchangeImagview = (ImageView) findViewById(R.id.change_bag);


        //1.import quotes from string.xml
        Resources res = getResources();
        String[] allQuotes = res.getStringArray(R.array.quote);
        String[] allAuthors = res.getStringArray(R.array.author);
        quoteList = new ArrayList<>();
        addtoQuoteList(allQuotes, allAuthors);

        previousQuotes = new Stack<>();



        //2.generate random quote when start
        final int quotesLength = quoteList.size();
        index  = getRandomQuote(quotesLength);
        quoteTextView.setText(quoteList.get(index).toString());


        //3.generate random quotes for next button
        nextButtonImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrevious = false;
                index  = getRandomQuote(quotesLength);
                quoteTextView.setText(quoteList.get(index).toString());
                previousQuotes.push(quoteList.get(index));
            }
        });

        //4.recall previous quotr for back button
        prevButtonImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPrevious && previousQuotes.size() > 0){
                    previousQuotes.pop();
                    isPrevious = true;
                }
                if(previousQuotes.size() > 0 && isPrevious)
                  quoteTextView.setText(previousQuotes.pop().toString());
                else
                    Toast.makeText(MainActivity.this, "no previous quote", Toast.LENGTH_SHORT).show();
            }
        });

        // 5.copy the quote
        copyButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Edittext", quoteTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();


            }
        });
        //7. chnge background
        bagchangeImagview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image++;
                        current_image = current_image % images.length;
                        bagImageView.setImageResource(images[current_image]);
                    }
                }
        );


        //6.share on social media
        shareButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT , quoteList.get(index).toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }
    // adding quote ton quotelist
    public void addtoQuoteList( String[] allQuotes, String[] allAuthors) {
        for (int i=0; i<allQuotes.length;i++ ){

            String quote = allQuotes[i];
            String author = allAuthors[i];
            Quote newquote = new Quote(quote, author);
            quoteList .add(newquote);
        }
    }
    // Random Quotes
    public int getRandomQuote(int length){
        return (int)( Math.random() * length )+ 1;
    }


}

