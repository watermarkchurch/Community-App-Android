package com.watermark.community_app.communityapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.watermark.community_app.communityapp.R
import com.watermark.community_app.communityapp.data.CommunityQuestionsItem

class HomeCommunityQuestionsAdapter : RecyclerView.Adapter<HomeCommunityQuestionsViewHolder>() {

    private var communityQuestions = listOf<CommunityQuestionsItem>()
    private lateinit var callbacks: Callbacks

    fun swapData(questions: List<CommunityQuestionsItem>) {
        communityQuestions = questions
        notifyDataSetChanged()
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommunityQuestionsViewHolder {
        return HomeCommunityQuestionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_community_question_item, parent, false))
    }

    override fun getItemCount(): Int {
        return communityQuestions.size
    }

    override fun onBindViewHolder(holder: HomeCommunityQuestionsViewHolder, position: Int) {
        val itemData = communityQuestions[position]

        val expanded = itemData.isExpanded

        holder.description.visibility = if (expanded) View.VISIBLE else View.GONE
        holder.question.text = itemData.question
        holder.description.text = itemData.description

        holder.itemView.setOnClickListener {
            val expanded = itemData.isExpanded

            itemData.isExpanded = !expanded

            notifyItemChanged(position)
        }

    }

    interface Callbacks {
        fun onItemClicked(communityQuestionsItem: CommunityQuestionsItem)
    }

}

class HomeCommunityQuestionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var question: TextView = itemView.findViewById(R.id.question)
    var description: TextView = itemView.findViewById(R.id.description)

}