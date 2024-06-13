package com.example.behomeapp.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.behomeapp.DBManager.CalendarioManager;
import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.EventoModelo;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private TextView textViewDate;
    private RecyclerView recyclerViewEvents;
    private EventosAdapter eventosAdapter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d 'de' MMMM 'de' yyyy",  new Locale("es", "ES"));

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        textViewDate = view.findViewById(R.id.textViewDate);
        recyclerViewEvents = view.findViewById(R.id.recyclerViewEvents);
        Button buttonCrearEvento = view.findViewById(R.id.button);

        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        final String email = SharedPreferencesUtils.getUserEmail(requireContext());

        new Thread(() -> {
            final String pisoId = DataBaseManager.obtenerPisoId(email);

            requireActivity().runOnUiThread(() -> {
                // Configurar la fecha actual por defecto
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                updateDateAndEvents(currentDate, pisoId);

                // Configurar el listener de cambio de fecha
                calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);
                    Date selectedDate = selectedCalendar.getTime();
                    updateDateAndEvents(selectedDate, pisoId);
                });
            });
        }).start();

        buttonCrearEvento.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_navigation_calendar_to_crearEventoFragment));

        return view;
    }


    private void updateDateAndEvents(Date date, String pisoId) {
        // Mostrar la fecha en el TextView
        textViewDate.setText(dateFormat.format(date));

        // Obtener eventos de la base de datos y actualizar el RecyclerView
        new Thread(() -> {
            List<EventoModelo> eventos = CalendarioManager.obtenerEventosPorFecha(pisoId, date);
            requireActivity().runOnUiThread(() -> {
                eventosAdapter = new EventosAdapter(getContext(), eventos);
                recyclerViewEvents.setAdapter(eventosAdapter);
            });
        }).start();
    }
}