package ie.dit.britton.darren.shoppinglist;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


public class CheckoutView extends ActionBarActivity 
{

	Basket basket;
	Button checkout;
	Button modifyCart;
	TableLayout purchasesTable;
	TableRow purchase;
	NumberFormat currency;
	Toast toast;
	String mailBody;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        
        modifyCart =(Button)findViewById(R.id.modifyCart);
        modifyCart.setOnClickListener(
	        new OnClickListener()
	        {
	        	@Override
	            public void onClick(View view)
	            {
	        		finish();
	            }
	        });
        
        final Calendar calendar = Calendar.getInstance();
                
        checkout =(Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(
	        new OnClickListener()
	        {
	        	@Override
	            public void onClick(View view)
	            {
	        		
        		   class SendEmail extends AsyncTask<Void, Void, Void>
        		   {
	    		        @Override
	    		        protected Void doInBackground(Void... params) {
	    		        	 try {   
	    		                    GMailSender sender = new GMailSender("shoppinglistify@gmail.com",
	    		                    		"01189998819991197253");
	    		                    sender.sendMail("Your Personalised Shopping List for" + calendar.getTime(),   
	    		                            mailBody,   
	    		                            "no-reply@shoppinglistify.com",   
	    		                            User.getInstance().getEmail());   
	    		                } catch (Exception e) {   
	    		                    Log.e("SendMail", e.getMessage(), e);   
	    		                } 

							return null;
	    		        }
        		   }
        		  
        		   new SendEmail().execute();
        		   MakeToast("\nTransaction Completed\n Your Shopping List Has Been Emailed To You");
	                	        	
	            }
	        });
        
        currency = NumberFormat.getCurrencyInstance(Locale.ITALY);
        
        mailBody += "Name\tQuantity\tVAT\tPrice\tTotal";
        
        basket = Basket.getInstance();
        purchasesTable = (TableLayout) findViewById(R.id.purchases);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        OrderLine[] purchases = basket.getPurchases();
        
        for(OrderLine p : purchases)
        {
        	if(p.getQuantity() > 0)
        	{
            	purchase = new TableRow(this);
            	purchase.setLayoutParams(layoutParams);
            	TextView name = new TextView(this);
            	TextView quantity = new TextView(this);
            	TextView VAT = new TextView(this);
            	TextView price = new TextView(this);
            	TextView total = new TextView(this);
            	name.setText(p.getName());
            	quantity.setText(String.valueOf(p.getQuantity()));
            	mailBody += String.valueOf(p.getQuantity()) + "\t";
            	VAT.setText(String.valueOf(currency.format(p.getVat())));
            	mailBody += String.valueOf(currency.format(p.getVat())) + "\t";
            	price.setText(String.valueOf(currency.format(p.getPrice())));
            	mailBody += String.valueOf(currency.format(p.getPrice())) + "\t";
            	total.setText(String.valueOf(currency.format(p.getTotal())));
            	mailBody += String.valueOf(currency.format(p.getTotal())) + "\t";
            	mailBody += "\n";
            	name.setLayoutParams(layoutParams);
            	quantity.setLayoutParams(layoutParams);
            	VAT.setLayoutParams(layoutParams);
            	price.setLayoutParams(layoutParams);
            	total.setLayoutParams(layoutParams);
            	purchase.addView(name);
            	purchase.addView(quantity);
            	purchase.addView(VAT);
            	purchase.addView(price);
            	purchase.addView(total);
            	//tr.setBackgroundResource(R.drawable.sf_gradient_03);
            	purchasesTable.addView(purchase);
        	}
        }
        
      	purchase = new TableRow(this);
    	purchase.setLayoutParams(layoutParams);
    	TextView name = new TextView(this);
    	TextView quantity = new TextView(this);
    	TextView VAT = new TextView(this);
    	TextView price = new TextView(this);
    	TextView total = new TextView(this);
    	total.setText(String.valueOf(currency.format(basket.getTotal())));
    	mailBody += String.valueOf(currency.format(basket.getTotal())) + "\t";
    	mailBody += "\n";
    	layoutParams.setMargins(0, 5, 0, 0);
    	name.setLayoutParams(layoutParams);
    	quantity.setLayoutParams(layoutParams);
    	VAT.setLayoutParams(layoutParams);
    	price.setLayoutParams(layoutParams);
    	total.setLayoutParams(layoutParams);
    	purchase.addView(name);
    	purchase.addView(quantity);
    	purchase.addView(VAT);
    	purchase.addView(price);
    	purchase.addView(total);
    	//tr.setBackgroundResource(R.drawable.sf_gradient_03);
    	purchasesTable.addView(purchase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_detail_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	  switch (item.getItemId()) {
    	    case R.id.action_settings:
    	        // Settings option clicked.
    	        return true;
    	    default:
    	        return super.onOptionsItemSelected(item);
    	    }
    }
    
    public void MakeToast(String message)
    {
    	if (toast != null)
    	{
    		toast.cancel();
    	}
    	
    	toast =Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    	toast.show();
    }
}