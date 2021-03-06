package com.nguyenvantho.vanthosmarthome.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenvantho.vanthosmarthome.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment3<onPostResume> extends Fragment {

    LineChart mChart;
     int CheckNhietDo=0, CheckKhiGa=0,CheckdoAm=0,i=0;
     int[] NhietDoArr={60,50,20,50,30,90,60,50,20,50,30,90,10};
     int[] KhiGaArr={30,40,60,10,80,30,40,60,10,80,40,60,10};
    int[] DoAmArr={30,40,60,10,80,30,40,60,10,80,40,60,10};
     private  Handler handler;
     private Timer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_fragment3,container,false);

        mChart= view.<LineChart>findViewById(R.id.lineChart);

        handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==0)
                {
                    CheckNhietDo=1;
                    CheckKhiGa=1;
                    CheckdoAm=1;

                    mChart.setDragEnabled(true);
                    mChart.setScaleEnabled(false);
                    mChart.getDescription().setText("Nhi???t ????? , ????? ???m , Kh?? Ga");

                    YAxis leftAxis = mChart.getAxisLeft();
                    leftAxis.removeAllLimitLines();
                    leftAxis.setAxisMaximum(100f);
                    leftAxis.setAxisMinimum(0f);
                    leftAxis.enableAxisLineDashedLine(10f,10f,0f);
                    leftAxis.setDrawLimitLinesBehindData(true);

                    mChart.getAxisRight().setEnabled(false);

                    ArrayList<Entry> yValues=new ArrayList<>();
                    if(CheckNhietDo==1)
                    {
                        CheckNhietDo=0;
                        for(int i=0;i<=11;i++)
                        {
                            NhietDoArr[i]=NhietDoArr[i+1];
                        }
                    }
                    for(int i=0;i<=11;i++)
                    {
                        yValues.add(new Entry(i,NhietDoArr[i]));
                    }

                    LineDataSet set1 = new LineDataSet(yValues,"Nhi???t ?????");
/////////////////////////////////////////
                    ArrayList<Entry> yValues1=new ArrayList<>();

                    if(CheckKhiGa==1)
                    {
                        CheckKhiGa=0;
                        for(int i=0;i<=11;i++)
                        {
                            KhiGaArr[i]=KhiGaArr[i+1];
                        }
                    }
                    for(int i=0;i<=11;i++)
                    {
                        yValues1.add(new Entry(i,KhiGaArr[i]));
                    }


                    LineDataSet set2 = new LineDataSet(yValues1,"Kh?? Ga");
 ///////////////////////////////////////////////////////
                    ArrayList<Entry> yValues2=new ArrayList<>();

                    if(CheckdoAm==1)
                    {
                        CheckdoAm=0;
                        for(int i=0;i<=11;i++)
                        {
                            DoAmArr[i]=DoAmArr[i+1];
                        }
                    }
                    for(int i=0;i<=11;i++)
                    {
                        yValues2.add(new Entry(i,DoAmArr[i]));
                    }


                    LineDataSet set3 = new LineDataSet(yValues2,"????? ???m");
/////////////////////////////////////////////
                    set1.setFillAlpha(110);
                    set1.setColor(Color.RED);
                    set1.setLineWidth(3f);
                    set1.setValueTextSize(10f);
                    set1.setValueTextColor(Color.BLUE);

                    set2.setFillAlpha(110);
                    set2.setColor(Color.YELLOW);
                    set2.setLineWidth(3f);
                    set2.setValueTextSize(10f);
                    set2.setValueTextColor(Color.GREEN);

                    set3.setFillAlpha(110);
                    set3.setColor(Color.MAGENTA);
                    set3.setLineWidth(3f);
                    set3.setValueTextSize(10f);
                    set3.setValueTextColor(Color.RED);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    dataSets.add(set3);
                    dataSets.add(set2);

                    LineData data = new LineData(dataSets);
                    mChart.setData(data);
                    mChart.moveViewToX(1f);
                    data.notifyDataChanged();


                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,1000);

        DatabaseReference NhietDoData,KhiGaData, DoAmData;
        ///////////////////////Nhi???t ?????
        NhietDoData = FirebaseDatabase.getInstance().getReference("NhietDo");
        NhietDoData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dulieu= (String) snapshot.getValue();
                NhietDoArr[12]=Integer.parseInt(dulieu);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /////////////////////////// kh?? ga
        KhiGaData = FirebaseDatabase.getInstance().getReference("KhiGa");
        KhiGaData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dulieu= (String) snapshot.getValue();
                KhiGaArr[12]=Integer.parseInt(dulieu);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /////////////////////////// ????? ???m
        DoAmData = FirebaseDatabase.getInstance().getReference("DoAm");
        DoAmData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dulieu= (String) snapshot.getValue();
                DoAmArr[12]=Integer.parseInt(dulieu);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
