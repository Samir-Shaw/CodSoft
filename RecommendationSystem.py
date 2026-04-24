import pandas as pd
import os
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


def load_or_create_dataset(file_path):
    if not os.path.exists(file_path):
        data = {
            "title": [
                "The Dark Knight", "Avengers", "Joker", "Toy Story", "Inception",
                "Interstellar", "Titanic", "The Matrix", "Gladiator", "Avatar",
                "Iron Man", "Captain America", "Thor", "Spider-Man", "Doctor Strange",
                "Black Panther", "Frozen", "Finding Nemo", "The Lion King", "Shrek",
                "Harry Potter", "Lord of the Rings", "The Hobbit", "Jurassic Park", "King Kong"
            ],
            "genres": [
                "Action Crime", "Action Sci-Fi", "Crime Drama", "Animation Children", "Action Sci-Fi",
                "Sci-Fi Drama", "Romance Drama", "Sci-Fi Action", "Action Drama", "Sci-Fi Adventure",
                "Action Sci-Fi", "Action Adventure", "Action Fantasy", "Action Adventure", "Fantasy Sci-Fi",
                "Action Adventure", "Animation Musical", "Animation Adventure", "Animation Drama", "Animation Comedy",
                "Fantasy Adventure", "Fantasy Adventure", "Fantasy Adventure", "Adventure Sci-Fi", "Adventure Action"
            ]
        }
        pd.DataFrame(data).to_csv(file_path, index=False)
        print("Dataset created successfully.")

    return pd.read_csv(file_path)


def build_similarity_matrix(df):
    vectorizer = TfidfVectorizer(stop_words="english")
    matrix = vectorizer.fit_transform(df["genres"])
    return cosine_similarity(matrix)


def find_movie_index(df, name):
    name = name.strip().lower()

    # exact match first
    exact = df[df["title"].str.lower() == name]
    if not exact.empty:
        return exact.index[0]

    # partial match fallback
    partial = df[df["title"].str.lower().str.contains(name, na=False)]
    if not partial.empty:
        return partial.index[0]

    return None


def recommend_movies(movie_name, df, similarity_matrix, top_n=5):
    index = find_movie_index(df, movie_name)

    if index is None:
        print("\nMovie not found. Try these examples:")
        print(list(df["title"]))
        return

    scores = list(enumerate(similarity_matrix[index]))
    scores = sorted(scores, key=lambda x: x[1], reverse=True)[1:top_n+1]

    print(f"\nRecommendations for '{df['title'].iloc[index]}':\n")
    for i, score in scores:
        print(f"- {df['title'].iloc[i]} ({round(score * 100, 2)}% match)")


def main():
    file_path = "movies_dataset.csv"

    movies_df = load_or_create_dataset(file_path)
    similarity_matrix = build_similarity_matrix(movies_df)

    print("=== Movie Recommendation System ===")

    while True:
        user_input = input("\nEnter movie name (or type 'exit'): ")

        if user_input.lower() == "exit":
            print("Goodbye!")
            break

        recommend_movies(user_input, movies_df, similarity_matrix)


if __name__ == "__main__":
    main()