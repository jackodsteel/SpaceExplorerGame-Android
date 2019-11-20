package nz.co.jacksteel.spaceexplorer.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.CrewMember

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CrewMemeberFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CrewMemeberFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CrewMemeberFragment : Fragment() {

    private lateinit var crewNameTextView: TextView
    private lateinit var crewHealthBar: ProgressBar
    private lateinit var crewHungerBar: ProgressBar
    private lateinit var crewTirednessBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crew_memeber, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crewNameTextView = view.findViewById(R.id.crewNameTextView)
        crewHealthBar = view.findViewById(R.id.crewHealthBar)
        crewHungerBar = view.findViewById(R.id.crewHungerBar)
        crewTirednessBar = view.findViewById(R.id.crewTirednessBar)
    }

    fun setup(crewMember: CrewMember) {
        crewNameTextView.text = crewMember.name
        crewHealthBar.progress = crewMember.health
        crewHungerBar.progress = crewMember.hunger
        crewTirednessBar.progress = crewMember.tiredness
    }

}
