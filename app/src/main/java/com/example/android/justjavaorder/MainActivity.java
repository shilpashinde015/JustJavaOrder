package com.example.android.justjavaorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can not have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.editTextView);
        String name = text.getText().toString();

        CheckBox whippedcreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox choclateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean hasChocolate = choclateCheckBox.isChecked();
        boolean hasWhippedCream = whippedcreamCheckBox.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = CreateSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addchoclate, boolean addcreame) {
        int basePrice = 5;
        if (addcreame) {
            basePrice += 1;
        }
        if (addchoclate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    private String CreateSummary(String name, int price, boolean addCreame, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\n Add whipped cream?" + addCreame;
        priceMessage += "\n Add Chocolate?" + addChocolate;
        priceMessage += "\n Quantity:" + quantity;
        priceMessage += "\n Total: $" + price;
        priceMessage += "\n Thank You !";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

  }