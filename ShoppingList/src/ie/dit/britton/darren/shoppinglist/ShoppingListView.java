package ie.dit.britton.darren.shoppinglist;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		
		public ShoppingListAdapter(Context context,int resource,int textViewResourceId,String[] values)
		{
			super(context,resource,textViewResourceId,values);
			this.textViewResourceId = textViewResourceId;
			this.context = context;
			this.values = values;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(int position,  View view, ViewGroup parent) {
		    
			LayoutInflater inflater=getLayoutInflater();
			inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        View rowView = inflater.inflate(R.layout.row, parent, false);
		        TextView textView;
		        textView = (TextView) rowView.findViewById(textViewResourceId);
		        if(Character.isUpperCase(values[position].charAt(0)))
        		{
			        textView = (TextView)getLayoutInflater().inflate(R.layout.header, null);
        		}
		        textView.setText(values[position]);
		        return rowView;
		}

	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_view);
        Resources res = getResources();
        setListAdapter(new ShoppingListAdapter(this,R.layout.row,R.id.item,res.getStringArray(R.array.jobTitles)));
    }
    
    protected void onListItemClick(ListView l, View v,int position, long id)
    {
    	String message = l.getItemAtPosition(position).toString();
    	
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}