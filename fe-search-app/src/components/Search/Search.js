const Search = () => {
    return (<div className="card-body">
        <div id='search-box'>
            <form action='/search' id='search-form' method='get' target='_top'>
                <input id='search-text' name='q' placeholder='Search' type='text'></input>
                <button id='search-button' type='submit'>
                    <span>Search</span>
                </button>
            </form>
        </div>
    </div>)
}

export default Search;