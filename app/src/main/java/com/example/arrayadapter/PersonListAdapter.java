package com.example.arrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Person> {
 private static final String TAG = "PersonListAdapter";

 private Context mContext;
 private int mResource;
 private int lastPosition = -1;

 // Holds variables in a view
 private static class ViewHolder {
  TextView name, id, gender, birthday, program;
 }

/**
 * Default constructor for the PersonListAdapter
 * @param context
 * @param resource
 * @param objects
 **/
 public PersonListAdapter(Context context, int resource, ArrayList<Person> objects) {
  super(context, resource, objects);
  mContext = context;
  mResource = resource;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  // Get the persons information
  String name     = getItem(position).getName(),
         id       = getItem(position).getId(),
         gender   = getItem(position).getGender(),
         birthday = getItem(position).getBirthday(),
         program  = getItem(position).getProgram();

  // Create the person object with the information
  Person person = new Person(name, id, gender, birthday, program);

  // Create the view result for showing the animation
  final View result;

  // ViewHolder object
  ViewHolder holder;

  if (convertView == null) {
   LayoutInflater inflater = LayoutInflater.from(mContext);
   convertView = inflater.inflate(mResource, parent, false);

   holder = new ViewHolder();

   holder.name     = (TextView) convertView.findViewById(R.id.tvName);
   holder.id       = (TextView) convertView.findViewById(R.id.tvID);
   holder.gender   = (TextView) convertView.findViewById(R.id.tvGender);
   holder.birthday = (TextView) convertView.findViewById(R.id.tvBirthdate);
   holder.program  = (TextView) convertView.findViewById(R.id.tvProgram);

   result = convertView;

   convertView.setTag(holder);
  }

  else {
   holder = (ViewHolder) convertView.getTag();
   result = convertView;
  }

  Animation animation = AnimationUtils.loadAnimation(mContext,
  (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
  result.startAnimation(animation);
  lastPosition = position;

  holder.name.setText(person.getName());
  holder.id.setText(person.getId());
  holder.gender.setText(person.getGender());
  holder.birthday.setText(person.getBirthday());
  holder.program.setText(person.getProgram());

  return convertView;
 }
}
