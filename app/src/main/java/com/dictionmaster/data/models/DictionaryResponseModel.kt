package com.dictionmaster.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DictionaryResponseModel(
    @SerializedName("word") val word: String,
    @SerializedName("phonetic") val phonetic: String,
    @SerializedName("phonetics") val phonetics: List<Phonetic>,
    @SerializedName("origin") val origin: String,
    @SerializedName("meanings") val meanings: List<Meaning>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Phonetic) ?: emptyList(),
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Meaning) ?: emptyList()
    )

    // Function to get the default phonetic text
    fun getDefaultPhoneticText(): String? {
        // If the default phonetic text is available, return it
        if (!phonetic.isNullOrBlank()) {
            return phonetic
        }

        for (ph in phonetics) {
            if (!ph.text.isNullOrBlank()) {
                return ph.text
            }
        }

        return null
    }

    fun getDefaultPhoneticAudio(): String? {
        for (ph in phonetics) {
            if (!ph.audio.isNullOrBlank()) {
                return ph.audio
            }
        }

        return null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(phonetic)
        parcel.writeTypedList(phonetics)
        parcel.writeString(origin)
        parcel.writeTypedList(meanings)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DictionaryResponseModel> {
        override fun createFromParcel(parcel: Parcel): DictionaryResponseModel =
            DictionaryResponseModel(parcel)

        override fun newArray(size: Int): Array<DictionaryResponseModel?> =
            arrayOfNulls(size)
    }
}

data class Phonetic(
    @SerializedName("text") val text: String,
    @SerializedName("audio") val audio: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(audio)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Phonetic> {
        override fun createFromParcel(parcel: Parcel): Phonetic = Phonetic(parcel)

        override fun newArray(size: Int): Array<Phonetic?> = arrayOfNulls(size)
    }
}

data class Meaning(
    @SerializedName("partOfSpeech") val partOfSpeech: String,
    @SerializedName("definitions") val definitions: List<Definition>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Definition) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(partOfSpeech)
        parcel.writeTypedList(definitions)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Meaning> {
        override fun createFromParcel(parcel: Parcel): Meaning = Meaning(parcel)

        override fun newArray(size: Int): Array<Meaning?> = arrayOfNulls(size)
    }
}

data class Definition(
    @SerializedName("definition") val definition: String,
    @SerializedName("example") val example: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(definition)
        parcel.writeString(example)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Definition> {
        override fun createFromParcel(parcel: Parcel): Definition = Definition(parcel)

        override fun newArray(size: Int): Array<Definition?> = arrayOfNulls(size)
    }
}
