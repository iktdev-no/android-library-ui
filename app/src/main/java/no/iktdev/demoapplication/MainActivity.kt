package no.iktdev.demoapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import no.iktdev.demoapplication.databinding.ActivityMainBinding
import no.iktdev.ui.NestedAdapterData
import no.iktdev.ui.NestedRecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.nested.apply {
            (this.adapter as NestedRecyclerView.NestedAdapter).add(
                NestedAdapterData("0", "Test", PreviewAdapter())
            )
        }

    }
}