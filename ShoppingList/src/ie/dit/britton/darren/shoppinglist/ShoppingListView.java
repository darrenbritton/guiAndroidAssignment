package ie.dit.britton.darren.shoppinglist;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingListView extends ListActivity {
	@SuppressLint("ViewHolder")
	public class ShoppingListAdapter  extends ArrayAdapter<String>
	{
		Context context;
		String[] values;
		int textViewResourceId;
		int plusResourceId;
		int minusResourceId;
		int quantityResourceId;
		
		public ShoppingListAdapter(Context context,int resource,int textViewResourceId,
				int plusResourceId, int minusResourceId, int quantityResourceId,String[] values)
		{
			super(context,resource,textViewResourceId,values);
			this.textViewResourceId = textViewResourceId;
			this.plusResourceId = plusResourceId;
			this.minusResourceId = minusResourceId;
			this.quantityResourceId = quantityResourceId;
			this.context = context;
			this.values = values;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(int position,  View view, ViewGroup parent) {
		    
			LayoutInflater inflater=getLayoutInflater();
			inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        View rowView = inflater.inflate(R.layout.row, parent, false);
		        TextView textView = (TextView) rowView.findViewById(textViewResourceId);
		        textView.setText(values[position]);
		        Button plus = (Button) rowView.findViewById(plusResourceId);
		        Button minus = (Button) rowView.findViewById(minusResourceId);
		        final EditText quantity = (EditText) rowView.findViewById(quantityResourceId);
		        
				plus.setOnClickListener(
	               new OnClickListener()
	               {
	               	@Override
	                   public void onClick(View view)
	                   {
	               		int itemQuantity = 0;
	               		String stringQuantity = quantity.getText().toString();
	               		
	               		if (stringQuantity.length() != 0)
	               		{
		               		  itemQuantity = Integer.parseInt(quantity.getText().toString());
	               		}
	               		
	               		itemQuantity++;
	               		quantity.setText(String.valueOf(itemQuantity));
	                   }
	               });
				
				minus.setOnClickListener(
	               new OnClickListener()
	               {
	               	@Override
	                   public void onClick(View view)
	                   {
	               		int itemQuantity = 0;
	               		String stringQuantity = quantity.getText().toString();
	               		
	               		if (stringQuantity.length() != 0)
	               		{
	               			System.out.println("StringQuantity: "+stringQuantity);
	               			itemQuantity = Integer.parseInt(quantity.getText().toString());
	               			if (itemQuantity > 0)
	               			{
		               			itemQuantity--;
	               			}
	               		}
	               		quantity.setText(String.valueOf(itemQuantity));
	                   }
	               });
		        
		        return rowView;
		}

	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_view);
        Resources res = getResources();
        setListAdapter(new ShoppingListAdapter(this,R.layout.row,R.id.item,R.id.plus,R.id.minus,R.id.quantity,res.getStringArray(R.array.items)));
    }
    
    protected void onListItemClick(ListView l, View v,int position, long id)
    {
    	String message = l.getItemAtPosition(position).toString();
    	
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
