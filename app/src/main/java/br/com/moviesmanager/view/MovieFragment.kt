package br.com.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.moviesmanager.R
import br.com.moviesmanager.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {
    private lateinit var ftb: FragmentMovieBinding
    private val navigationArgs: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.movie_details)

        ftb = FragmentMovieBinding.inflate(inflater, container, false)

        val receivedTask = navigationArgs.movie
        receivedTask?.also { task ->
            with(ftb) {
                nameEt.setText(task.name)
                doneCb.isChecked = task.done == TASK_DONE_TRUE
                navigationArgs.editTask.also { editTask ->
                    nameEt.isEnabled = editTask
                    doneCb.isEnabled = editTask
                    saveBt.visibility = if (editTask) VISIBLE else GONE
                }
            }
        }

        ftb.run {
            saveBt.setOnClickListener {
                setFragmentResult(TASK_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_TASK, Task(
                            receivedTask?.time ?: System.currentTimeMillis(),
                            nameEt.text.toString(),
                            if (doneCb.isChecked) TASK_DONE_TRUE else TASK_DONE_FALSE
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }

        return ftb.root
    }
}
