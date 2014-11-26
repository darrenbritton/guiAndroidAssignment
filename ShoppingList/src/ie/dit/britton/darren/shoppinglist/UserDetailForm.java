package ie.dit.britton.darren.shoppinglist;

import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class UserDetailForm extends ActionBarActivity
{

	Button submit;
	EditText name;
	RadioGroup gender;
	Spinner job;
	EditText age;
	EditText money;
	EditText email;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail_form);

		/**
		 * This thread uses Store's populate function to asynchronously load the
		 * shopping list items and thus reduce reduces the time it takes for the
		 * shopping list view intent to load. A thread was chosen over an
		 * asyncTask because the benefits it provides (parent child
		 * communication) was not required.
		 */
		if (!Store.getInstance().isPopulated())
		{
			new Thread() {
				@Override
				public void run()
				{
					Store.getInstance()
							.populate(
									getResources()
											.getStringArray(R.array.items),
									getResources().getStringArray(
											R.array.descriptions));
				}
			}.run();
		}

		int spinnerID = R.id.job;
		/**
		 * Here the spinner is populated with the job titles string array from
		 * strings.xml
		 */
		Spinner spinner = (Spinner) findViewById(spinnerID);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.jobTitles, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		submit = (Button) findViewById(R.id.submit);
		name = (EditText) findViewById(R.id.name);
		gender = (RadioGroup) findViewById(R.id.gender);
		job = (Spinner) findViewById(spinnerID);
		age = (EditText) findViewById(R.id.age);
		money = (EditText) findViewById(R.id.money);
		email = (EditText) findViewById(R.id.email);

		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View view)
			{
				/**
				 * decides whether the next intent should be loaded e.g. if all
				 * the required input fields have be completed with reasonable
				 * data.
				 */
				boolean nextIntent = setUserDetails();
				if (nextIntent)
				{
					loadShoppingList();
				} else
				{
					Display.makeToast(
							"Please complete all fields / Enter valid values",
							getBaseContext());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.user_detail_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_load_demo_values:
			Display.makeToast("this feature was deemed less important",
					getBaseContext());
			return true;
		case R.id.action_settings:
			// Settings option clicked.
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean setUserDetails()
	{
		User user = User.getInstance();

		/**
		 * regex pattern matching was used to ensure data entered was in the
		 * desired format
		 */
		String nameRegex = "^[A-z ,.'-]+$";
		String ageRegex = "^1[0-9][0-9]$|^[1-9][0-9]$|^[0-9]$";
		String moneyRegex = "[0-9]+";
		String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

		if (name.getText().length() != 0
				&& Pattern.matches(nameRegex, name.getText().toString()))
		{
			user.setName(name.getText().toString());
		} else
		{
			return false;
		}

		int selectedID = gender.getCheckedRadioButtonId();
		RadioButton selectedGender = (RadioButton) findViewById(selectedID);

		if (selectedGender.getText().length() != 0)
		{
			user.setGender(selectedGender.getText().toString());
		} else
		{
			return false;
		}

		user.setJob(job.getSelectedItem().toString());

		if (age.getText().length() != 0
				&& Pattern.matches(ageRegex, age.getText().toString()))
		{
			user.setAge(Short.parseShort(age.getText().toString()));
		} else
		{
			return false;
		}

		if (money.getText().length() != 0
				&& Pattern.matches(moneyRegex, money.getText().toString()))
		{
			user.setMoney(Double.parseDouble(money.getText().toString()));
		} else
		{
			return false;
		}

		if (email.getText().length() != 0
				&& Pattern.matches(emailRegex, email.getText().toString()))
		{
			user.setEmail(email.getText().toString());
		} else
		{
			return false;
		}

		// Log.i("info","Name: " +user.getName());
		// Log.i("info","Gender: " +user.getGender());
		// Log.i("info","Job: " +user.getJob());
		// Log.i("info","Age: " +user.getAge());
		// Log.i("info","Money: " +user.getMoney());
		// Log.i("info","Email: " +user.getEmail());

		return true;

	}

	private void loadShoppingList()
	{
		Intent intent = new Intent(UserDetailForm.this, ShoppingListView.class);
		startActivity(intent);
	}
}