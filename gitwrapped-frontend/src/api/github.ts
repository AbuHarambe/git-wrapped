import axios from 'axios';

const API_BASE_URL = '/api/github';

/**
 * Ruft GitHub-User-Daten über das Backend ab.
 * @param username GitHub-Username
 * @returns Benutzerinformationen (als JSON-String)
 */
export async function fetchGitHubUser(username: string): Promise<string> {
  try {
    const response = await axios.get(`${API_BASE_URL}/${encodeURIComponent(username)}`);
    return response.data;
  } catch (error) {
    console.debug('Fehler beim Abrufen von GitHub-Daten:', error);
    throw error;
  }
}
