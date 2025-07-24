package com.example.easybot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<messageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "AIzaSyDEILGuw72maJneFY9FtE4w43-FVbq2Yz4"
    )
    fun sendMessage(question : String){

        viewModelScope.launch {

            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){text(it.message)}
                    }.toList()
                )

                messageList.add(messageModel(question,"user"))
                messageList.add(messageModel("Typing...","model"))

                val response = chat.sendMessage(question)
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.lastIndex)
                }
                messageList.add(messageModel(response.text.toString(),"model"))
            }
            catch (e : Exception){
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.lastIndex)
                }
                messageList.add(messageModel("Error"+e.message.toString(),"model"))
            }

        }
    }
}