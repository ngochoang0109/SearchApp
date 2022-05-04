import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { documentAction } from "../../action/documentAction";

const Search = () => {

    const dispatch = useDispatch();

    const [index, setIndex] = useState('');

    const data = useSelector(state => state.documentReducer);

    const handlerOnChange = (event) => {
        setIndex(event.target.value)
    }

    const onSubmit = (event) => {
        event.preventDefault();
        dispatch(documentAction.getAllOfIndex(index));
    }

    const showTable = () => {
        if (data.length > 0) {
            return <table className="table">
                <thead>
                    <tr>
                        <th>id</th>
                        <th>title</th>
                        <th>content</th>
                        <th>keyword</th>
                    </tr>
                </thead>
                <tbody>
                    {showData()}
                </tbody>
            </table>
        }
    }

    const showData = () => {
        return data.map((item) => {
            return <tr>
                <td>{item.id}</td>
                <td>{item.title}</td>
                <td>{item.keyword}</td>
                <td>{item.content}</td>
            </tr>
        })
    }

    return (<div className="card-body">
        <div id='search-box'>
            <form action='/search' id='search-form' method='get' target='_top' onSubmit={onSubmit}>
                <input id='search-text' name='q' placeholder='Search' type='text' onChange={handlerOnChange}></input>

                <button id='search-button' type='submit'>
                    <span>Search</span>
                </button>
            </form>

        </div>
        
        {showTable()}
    </div>)
}

export default Search;